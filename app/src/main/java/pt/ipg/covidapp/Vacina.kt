package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacina(var id: Long = -1, var nome: String, var dosagem: Int, var quantidade: Int)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinas.CAMPO_NOME, nome)
            put(TabelaVacinas.CAMPO_DOSAGEM, dosagem)
            put(TabelaVacinas.CAMPO_QUANTIDADE, quantidade)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaVacinas.CAMPO_NOME)
            val colDosagem = cursor.getColumnIndex(TabelaVacinas.CAMPO_DOSAGEM)
            val colQuantidade = cursor.getColumnIndex(TabelaVacinas.CAMPO_QUANTIDADE)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val dosagem = cursor.getInt(colDosagem)
            val quantidade = cursor.getInt(colQuantidade)

            return Vacina(id, nome, dosagem, quantidade)
        }
    }
}