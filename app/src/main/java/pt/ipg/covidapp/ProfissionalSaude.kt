package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ProfissionalSaude(var id: Long = -1, var NomeProfissional: String)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaProfissionalSaude.CAMPO_NOME, NomeProfissional)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ProfissionalSaude {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNomeProfissional = cursor.getColumnIndex(TabelaProfissionalSaude.CAMPO_NOME)

            val id = cursor.getLong(colId)
            val NomeProfissional = cursor.getString(colNomeProfissional)

            return ProfissionalSaude(id, NomeProfissional)
        }
    }
}