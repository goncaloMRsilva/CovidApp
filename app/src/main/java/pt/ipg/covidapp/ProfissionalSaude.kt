package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ProfissionalSaude(var id: Long = -1, var NomeProfissional: String, var IDCargo: Long, var NomeCargo: String? = null)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaProfissionalSaude.CAMPO_NOME, NomeProfissional)
            put(TabelaProfissionalSaude.CAMPO_ID_CARGO, IDCargo)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ProfissionalSaude {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNomeProfissional = cursor.getColumnIndex(TabelaProfissionalSaude.CAMPO_NOME)
            val colIDCargo = cursor.getColumnIndex(TabelaProfissionalSaude.CAMPO_ID_CARGO)
            val colNomeCargo = cursor.getColumnIndex(TabelaProfissionalSaude.CAMPO_EXTERNO_NOME_CARGO)

            val id = cursor.getLong(colId)
            val NomeProfissional = cursor.getString(colNomeProfissional)
            val IDCargo = cursor.getLong(colIDCargo)
            val NomeCargo = if (colNomeCargo != -1) cursor.getString(colNomeCargo) else null

            return ProfissionalSaude(id, NomeProfissional, IDCargo, NomeCargo)
        }
    }
}