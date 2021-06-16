package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Cargo(var id: Long = -1, var funcaoProfissional: String)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaCargo.CAMPO_FUNCAO, funcaoProfissional)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Cargo {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colFuncaoProfissional = cursor.getColumnIndex(TabelaCargo.CAMPO_FUNCAO)

            val id = cursor.getLong(colId)
            val funcaoProfissional = cursor.getString(colFuncaoProfissional)

            return Cargo(id, funcaoProfissional)
        }
    }
}