package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaVacinas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DOSAGEM TEXT NOT NULL, $CAMPO_QUANTIDADE INTEGER NOT NULL)")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaVacinas.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaVacinas.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaVacinas.NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(TabelaVacinas.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME_TABELA = "Vacinas"
        const val CAMPO_NOME = "Nome"
        const val CAMPO_DOSAGEM = "Dosagem"
        const val CAMPO_QUANTIDADE = "Quantidade"

        val TODOS_CAMPOS =arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_DOSAGEM, CAMPO_QUANTIDADE)
    }

}