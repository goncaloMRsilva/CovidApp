package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaUtentes(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_IDADE INTEGER NOT NULL, $CAMPO_DOSAGEM1 INTEGER NOT NULL, $CAMPO_DOSAGEM2 INTEGER NOT NULL)")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaUtentes.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaUtentes.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaUtentes.NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(TabelaUtentes.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME_TABELA = "Utente"
        const val CAMPO_NOME = "NomeUtente"
        const val CAMPO_IDADE = "IdadeUtente"
        const val CAMPO_DOSAGEM1 = "Dosagem1"
        const val CAMPO_DOSAGEM2 = "Dosagem2"
    }
}