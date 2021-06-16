package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacina(var id: Long = -1, var nome: String, var quantidade: Int)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinas.CAMPO_NOME, nome)
            put(TabelaVacinas.CAMPO_QUANTIDADE, quantidade)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaVacinas.CAMPO_NOME)
            val colQuantidade = cursor.getColumnIndex(TabelaVacinas.CAMPO_QUANTIDADE)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val quantidade = cursor.getInt(colQuantidade)

            return Vacina(id, nome, quantidade)
        }
    }
}