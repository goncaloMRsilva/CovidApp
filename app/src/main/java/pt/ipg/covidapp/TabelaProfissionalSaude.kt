package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaProfissionalSaude(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL,  $CAMPO_ID_CARGO INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_CARGO) REFERENCES ${TabelaCargo.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaProfissionalSaude.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaProfissionalSaude.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaProfissionalSaude.NOME_TABELA, whereClause, whereArgs)
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

        var posColNomeCargo = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_CARGO) {
                posColNomeCargo = i
                break
            }
        }

        if (posColNomeCargo == -1) {
            return db.query(TabelaProfissionalSaude.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeCargo) {
                "${TabelaCargo.NOME_TABELA}.${TabelaCargo.CAMPO_FUNCAO} AS $CAMPO_EXTERNO_NOME_CARGO"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaCargo.NOME_TABELA} ON ${TabelaCargo.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_CARGO"

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
        const val NOME_TABELA = "ProfissionalSaude"
        const val CAMPO_NOME = "NomeProfissional"
        const val CAMPO_ID_CARGO = "IDCargo"        //chave estrangeira
        const val CAMPO_EXTERNO_NOME_CARGO = "NomeCargo"

        val TODOS_CAMPOS =arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_ID_CARGO, CAMPO_EXTERNO_NOME_CARGO)
    }
}