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
        return db.query(TabelaDosagem.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME_TABELA = "Dosagem"
        const val CAMPO_DATA = "DataAdministracao"
        const val CAMPO_DOSAGEM = "Dose"
        const val CAMPO_ID_UTENTE = "IdUtente"
        const val CAMPO_ID_VACINA = "IdVacina"
        const val CAMPO_ID_PF = "IdProfSaude"

        val TODOS_CAMPOS =arrayOf(BaseColumns._ID, CAMPO_DATA, CAMPO_DOSAGEM, CAMPO_ID_UTENTE, CAMPO_ID_VACINA, CAMPO_ID_PF)
    }
}