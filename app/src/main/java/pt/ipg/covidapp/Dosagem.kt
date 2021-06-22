package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Dosagem(var id: Long = -1, var DataAdministracao: Int, var Dose: Int, var IdUtente: Long, var IdVacina: Long)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaDosagem.CAMPO_DATA, DataAdministracao)
            put(TabelaDosagem.CAMPO_DOSAGEM, Dose)
            put(TabelaDosagem.CAMPO_ID_UTENTE, IdUtente)
            put(TabelaDosagem.CAMPO_ID_VACINA, IdVacina)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Dosagem {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colDataAdministracao = cursor.getColumnIndex(TabelaDosagem.CAMPO_DATA)
            val colDose = cursor.getColumnIndex(TabelaDosagem.CAMPO_DOSAGEM)
            val colIdUtente = cursor.getColumnIndex(TabelaDosagem.CAMPO_ID_UTENTE)
            val colIdVacina = cursor.getColumnIndex(TabelaDosagem.CAMPO_ID_VACINA)

            val id = cursor.getLong(colId)
            val DataAdministracao = cursor.getInt(colDataAdministracao)
            val Dose = cursor.getInt(colDose)
            val IdUtente = cursor.getLong(colIdUtente)
            val IdVacina = cursor.getLong(colIdVacina)

            return Dosagem(id, DataAdministracao, Dose, IdUtente, IdVacina)
        }
    }
}