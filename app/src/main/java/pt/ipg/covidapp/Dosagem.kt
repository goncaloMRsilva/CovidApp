package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Dosagem(var id: Long = -1, var DataAdministracao: Date, var Dose: Int, var IdUtente: Long, var IdVacina: Long, var IdProfSaude: Long)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaDosagem.CAMPO_DATA, DataAdministracao.time)
            put(TabelaDosagem.CAMPO_DOSAGEM, Dose)
            put(TabelaDosagem.CAMPO_ID_UTENTE, IdUtente)
            put(TabelaDosagem.CAMPO_ID_VACINA, IdVacina)
            put(TabelaDosagem.CAMPO_ID_PF, IdProfSaude)
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
            val colIdProfSaude = cursor.getColumnIndex(TabelaDosagem.CAMPO_ID_PF)

            val id = cursor.getLong(colId)
            val DataAdministracao = cursor.getLong(colDataAdministracao)
            val Dose = cursor.getInt(colDose)
            val IdUtente = cursor.getLong(colIdUtente)
            val IdVacina = cursor.getLong(colIdVacina)
            val IdProfSaude = cursor.getLong(colIdProfSaude)

            return Dosagem(id, Date(DataAdministracao), Dose, IdUtente, IdVacina, IdProfSaude)
        }
    }
}