package pt.ipg.covidapp

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdAdministracaoOpenHelper() = BdAdministracaoOpenHelper(getAppContext())

    private fun getTabelaUtentes(db: SQLiteDatabase) = TabelaUtentes(db)
    private fun getTabelaVacinas(db: SQLiteDatabase) = TabelaVacinas(db)
    private fun getTabelaDoses(db: SQLiteDatabase) = TabelaProfissionalSaude(db)

    private fun insereUtente(tabelaUtentes: TabelaUtentes, utente: Utentes): Long {
        val id = tabelaUtentes.insert(utente.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereVacina(tabelaVacinas: TabelaVacinas, vacina: Vacina): Long {
        val id = tabelaVacinas.insert(vacina.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereProfissionalSaude(tabelaProfissionalSaude: TabelaProfissionalSaude, profissionalSaude: ProfissionalSaude): Long {
        val id = tabelaProfissionalSaude.insert(profissionalSaude.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun getUtenteBD(
            tabelaUtentes: TabelaUtentes, id: Long    ): Utentes {
        val cursor = tabelaUtentes.query(
                TabelaUtentes.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Utentes.fromCursor(cursor)
    }

    private fun getVacinaBD(
            tabelaVacinas: TabelaVacinas, id: Long    ): Vacina {
        val cursor = tabelaVacinas.query(
                TabelaVacinas.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Vacina.fromCursor(cursor)
    }

    private fun getDosesBD(
            tabelaProfissionalSaude: TabelaProfissionalSaude, id: Long  ): ProfissionalSaude {
        val cursor = tabelaProfissionalSaude.query(
                TabelaProfissionalSaude.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return ProfissionalSaude.fromCursor(cursor)
    }


    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdAdministracaoOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBdAdministracaoOpenHelper().readableDatabase
        assert(db.isOpen)

        db.close()
    }
}