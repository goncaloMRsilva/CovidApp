package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Utente(var id: Long = -1, var nomeUtente: String, var dataNascimento: Date)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaUtente.CAMPO_NOME, nomeUtente)
            put(TabelaUtente.CAMPO_DATA_NASCIMENTO, dataNascimento.time)
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
            val DataNascimento = cursor.getLong(colDataNascimento)

            return Utente(id, NomeUtente, Date(DataNascimento))
        }
    }
}