package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Utentes(var id: Long = -1, var Utente: String, var NomeUtente: String, var IdadeUtente: Int, var Dosagem1: Int, var Dosagem2: Int)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaUtentes.NOME_TABELA, Utente)
            put(TabelaUtentes.CAMPO_NOME, NomeUtente)
            put(TabelaUtentes.CAMPO_IDADE, IdadeUtente)
            put(TabelaUtentes.CAMPO_DOSAGEM1, Dosagem1)
            put(TabelaUtentes.CAMPO_DOSAGEM2, Dosagem2)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Utentes {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colUtente = cursor.getColumnIndex(TabelaUtentes.NOME_TABELA)
            val colNomeUtente = cursor.getColumnIndex(TabelaUtentes.CAMPO_NOME)
            val colIdadeUtente = cursor.getColumnIndex(TabelaUtentes.CAMPO_IDADE)
            val colDosagem1 = cursor.getColumnIndex(TabelaUtentes.CAMPO_DOSAGEM1)
            val colDosagem2 = cursor.getColumnIndex(TabelaUtentes.CAMPO_DOSAGEM2)

            val id = cursor.getLong(colId)
            val Utente = cursor.getString(colUtente)
            val NomeUtente = cursor.getString(colNomeUtente)
            val IdadeUtente = cursor.getInt(colIdadeUtente)
            val Dosagem1 = cursor.getInt(colDosagem1)
            val Dosagem2 = cursor.getInt(colDosagem2)

            return Utentes(id, Utente, NomeUtente, IdadeUtente, Dosagem1, Dosagem2)
        }
    }
}