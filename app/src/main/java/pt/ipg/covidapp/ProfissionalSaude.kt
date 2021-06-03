package pt.ipg.covidapp

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class ProfissionalSaude(var id: Long = -1, var ProfissionalSaude: String, var NomeProfissional: String, var FuncaoProfissional: String)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaProfissionalSaude.NOME_TABELA, ProfissionalSaude)
            put(TabelaProfissionalSaude.CAMPO_NOME, NomeProfissional)
            put(TabelaProfissionalSaude.CAMPO_FUNCAO, FuncaoProfissional)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): ProfissionalSaude {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colProfissionalSaude = cursor.getColumnIndex(TabelaProfissionalSaude.NOME_TABELA)
            val colNomeProfissional = cursor.getColumnIndex(TabelaProfissionalSaude.CAMPO_NOME)
            val colFuncaoProfissional = cursor.getColumnIndex(TabelaProfissionalSaude.CAMPO_FUNCAO)

            val id = cursor.getLong(colId)
            val ProfissionalSaude = cursor.getString(colProfissionalSaude)
            val NomeProfissional = cursor.getString(colNomeProfissional)
            val FuncaoProfissional = cursor.getString(colFuncaoProfissional)

            return ProfissionalSaude(id, ProfissionalSaude, NomeProfissional, FuncaoProfissional)
        }
    }
}