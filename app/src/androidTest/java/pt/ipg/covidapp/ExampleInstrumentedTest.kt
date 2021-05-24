package pt.ipg.covidapp

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