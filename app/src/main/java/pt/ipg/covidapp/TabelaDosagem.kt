package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaDosagem(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DATA INTEGER NOT NULL, $CAMPO_DOSAGEM INTEGER NOT NULL, $CAMPO_ID_UTENTE LONG NOT NULL, $CAMPO_ID_VACINA LONG NOT NULL, $CAMPO_ID_PF LONG NOT NULL, FOREIGN KEY ($CAMPO_ID_UTENTE) REFERENCES ${TabelaDosagem.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaDosagem.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_PF) REFERENCES ${TabelaDosagem.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaDosagem.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaDosagem.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaDosagem.NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
            columns: Array<String>,
            selection: String?,
            selectionArgs: Array<String>?,
            groupBy: String?,
            having: String?,
            orderBy: String?
    ): Cursor? {

        val ultimaColuna = columns.size - 1

        var posColNomeUtente = -1
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_UTENTE) {
                posColNomeUtente = i
                break
            }
        }

        var posColNomeVacina = -1
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_VACINA) {
                posColNomeVacina = i
                break
            }
        }

        var posColNomeProfissionalSaude = -1
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_PROFISSIONALSAUDE) {
                posColNomeProfissionalSaude = i
                break
            }
        }

        if (posColNomeUtente == -1 || posColNomeVacina == -1 || posColNomeProfissionalSaude == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeUtente) {
                "${TabelaUtente.NOME_TABELA}.${TabelaUtente.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_UTENTE"
            } else if (i == posColNomeVacina) {
                "${TabelaVacinas.NOME_TABELA}.${TabelaVacinas.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_VACINA"
            }else if (i == posColNomeProfissionalSaude) {
                "${TabelaVacinas.NOME_TABELA}.${TabelaProfissionalSaude.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_PROFISSIONALSAUDE"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaUtente.NOME_TABELA} ON ${TabelaUtente.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_UTENTE INNER JOIN ${TabelaVacinas.NOME_TABELA} ON ${TabelaVacinas.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_VACINA INNER JOIN ${TabelaProfissionalSaude.NOME_TABELA} ON ${TabelaProfissionalSaude.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_PF"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)
    }

    companion object {
        const val NOME_TABELA = "Dosagem"
        const val CAMPO_DATA = "DataAdministracao"
        const val CAMPO_DOSAGEM = "Dose"
        const val CAMPO_ID_UTENTE = "IdUtente"
        const val CAMPO_ID_VACINA = "IdVacina"
        const val CAMPO_ID_PF = "IdProfSaude"
        const val CAMPO_EXTERNO_NOME_UTENTE = "nomeUtente"
        const val CAMPO_EXTERNO_NOME_VACINA = "nomeVacina"
        const val CAMPO_EXTERNO_NOME_PROFISSIONALSAUDE = "nomeProfissionalSaude"

        val TODOS_CAMPOS =arrayOf(BaseColumns._ID, CAMPO_DATA, CAMPO_DOSAGEM, CAMPO_ID_UTENTE, CAMPO_ID_VACINA, CAMPO_ID_PF, CAMPO_EXTERNO_NOME_UTENTE, CAMPO_EXTERNO_NOME_VACINA, CAMPO_EXTERNO_NOME_PROFISSIONALSAUDE)
    }
}