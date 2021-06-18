package pt.ipg.covidapp;

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Dosagem(var id: Long = -1, var NomeUtente: String, var DataNascimento: Int)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaUtente.CAMPO_NOME, Dosagem)
            put(TabelaUtente.CAMPO_DATA_NASCIMENTO, DataNascimento)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Utente {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNomeUtente = cursor.getColumnIndex(TabelaUtente.CAMPO_NOME)
            val colDataNascimento = cursor.getColumnIndex(TabelaUtente.CAMPO_DATA_NASCIMENTO)

            val id = cursor.getLong(colId)
            val NomeUtente = cursor.getString(colNomeUtente)
            val DataNascimento = cursor.getInt(colDataNascimento)

            return Utente(id, NomeUtente, DataNascimento)
        }
    }
}