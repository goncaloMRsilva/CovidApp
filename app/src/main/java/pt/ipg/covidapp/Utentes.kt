package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Utentes(var id: Long = -1, var Utente: String, var NomeUtente: String, var DataNascimento: Int, var DataDosagem1: Int, var DataDosagem2: Int)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaUtentes.NOME_TABELA, Utente)
            put(TabelaUtentes.CAMPO_NOME, NomeUtente)
            put(TabelaUtentes.CAMPO_DATA_NASCIMENTO, DataNascimento)
            put(TabelaUtentes.CAMPO_DOSAGEM1, DataDosagem1)
            put(TabelaUtentes.CAMPO_DOSAGEM2, DataDosagem2)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Utentes {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colUtente = cursor.getColumnIndex(TabelaUtentes.NOME_TABELA)
            val colNomeUtente = cursor.getColumnIndex(TabelaUtentes.CAMPO_NOME)
            val colDataNascimento = cursor.getColumnIndex(TabelaUtentes.CAMPO_DATA_NASCIMENTO)
            val colDataDosagem1 = cursor.getColumnIndex(TabelaUtentes.CAMPO_DOSAGEM1)
            val colDataDosagem2 = cursor.getColumnIndex(TabelaUtentes.CAMPO_DOSAGEM2)

            val id = cursor.getLong(colId)
            val Utente = cursor.getString(colUtente)
            val NomeUtente = cursor.getString(colNomeUtente)
            val DataNascimento = cursor.getInt(colDataNascimento)
            val Dosagem1 = cursor.getInt(colDataDosagem1)
            val Dosagem2 = cursor.getInt(colDataDosagem2)

            return Utentes(id, Utente, NomeUtente, DataNascimento, Dosagem1, Dosagem2)
        }
    }
}