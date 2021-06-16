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
    private fun getTabelaProfissionalSaude(db: SQLiteDatabase) = TabelaProfissionalSaude(db)
    private fun getTabelaCargo(db: SQLiteDatabase) = TabelaCargo(db)


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

    private fun insereCargo(tabelaCargo: TabelaCargo, cargo: Cargo): Long {
        val id = tabelaCargo.insert(cargo.toContentValues())
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

    private fun getProfissionalSaudeBD(
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

    private fun getCargoBD(
            tabelaCargo: TabelaCargo, id: Long  ): Cargo {
        val cursor = tabelaCargo.query(
                TabelaCargo.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Cargo.fromCursor(cursor)
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

    @Test
    fun consegueInserirVacinas(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina( nome = "AstraZeneca", quantidade = 1000)

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)
        db.close()
    }

    @Test
    fun consegueAlterarVacinas(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nome = "", quantidade = 0)

        vacina.id = insereVacina(tabelaVacinas, vacina)

        vacina.nome = "Pfizer"
        vacina.quantidade = 998

        val registosAlterados = tabelaVacinas.update(
                vacina.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)
        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)

        db.close()
    }

    @Test
    fun consegueApagarVacinas(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nome = "?", quantidade = 990 )

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val registosEliminados = tabelaVacinas.delete(
                "${BaseColumns._ID}=?",
                arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerVacinas(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nome = "", quantidade = 2000)

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)

        db.close()
    }

    @Test
    fun consegueInserirUtentes(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utentes(NomeUtente= "Goncalo Silva", DataNascimento = 17021999, DataDosagem1 = 9062021 , DataDosagem2 = 9092021 )

        utente.id = insereUtente(tabelaUtente, utente)
        val utenteBD = getUtenteBD(tabelaUtente, utente.id)
        assertEquals(utente, utenteBD)
        db.close()
    }

    @Test
    fun consegueAlterarUtentes(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaUtentes(db)
        val utente = Utentes(NomeUtente= "?", DataNascimento = 0, DataDosagem1 = 0, DataDosagem2 = 0)

        utente.id = insereUtente(tabelaUtentes, utente)
        utente.NomeUtente= "Adriano Lameiras"
        utente.DataNascimento= 2051998
        utente.DataDosagem1= 1
        utente.DataDosagem2 = 2
        val registosAlterados = tabelaUtentes.update(
                utente.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf(utente.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarUtentes(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaUtentes(db)
        val utente = Utentes(NomeUtente= "?", DataNascimento = 0, DataDosagem1 = 0, DataDosagem2 = 0)

        utente.id = insereUtente(tabelaUtentes, utente)

        val registosEliminados =tabelaUtentes.delete(
                "${BaseColumns._ID}=?",
                arrayOf(utente.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerUtentes(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaUtentes(db)
        val utente = Utentes(NomeUtente= "?", DataNascimento = 0, DataDosagem1 = 0, DataDosagem2 = 0)

        utente.id = insereUtente(tabelaUtentes, utente)

        val utenteBD = getUtenteBD(tabelaUtentes, utente.id)
        assertEquals(utente, utenteBD)

        db.close()
    }



    @Test
    fun consegueInserirProfissionalSaude(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira")

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)
        val ProfissionalSaudeBD = getProfissionalSaudeBD(tabelaProfissionalSaude, ProfissionalSaude.id)
        assertEquals(ProfissionalSaude, ProfissionalSaudeBD)
        db.close()
    }


    @Test
    fun consegueAlterarProfissionalSaude(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db);
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira")

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)

        ProfissionalSaude.NomeProfissional = "Augusto Soares"

        val registosAlterados = tabelaProfissionalSaude.update(
                ProfissionalSaude.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf(ProfissionalSaude.id.toString())
        )

        assertEquals(1, registosAlterados)
        val ProfissionalSaudeBD = getProfissionalSaudeBD(tabelaProfissionalSaude, ProfissionalSaude.id)
        assertEquals(ProfissionalSaude, ProfissionalSaudeBD)

        db.close()
    }

    @Test
    fun consegueApagarProfissionalSaude(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "?")

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)

        val registosEliminados =tabelaProfissionalSaude.delete(
                "${BaseColumns._ID}=?",
                arrayOf(ProfissionalSaude.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerProfissionalSaude(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "?")

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)

        val ProfissionalSaudeBD = getProfissionalSaudeBD(tabelaProfissionalSaude, ProfissionalSaude.id)
        assertEquals(ProfissionalSaude, ProfissionalSaudeBD)

        db.close()
    }

    @Test
    fun consegueInserirCargo(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Enfermeiro")

        cargo.id = insereCargo(tabelaCargo, cargo)
        val cargoBD = getCargoBD(tabelaCargo, cargo.id)
        assertEquals(cargo, cargoBD)
        db.close()
    }

    @Test
    fun consegueAlterarCargo(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaCargo = getTabelaCargo(db);
        val cargo = Cargo(funcaoProfissional = "?")

        cargo.id = insereCargo(tabelaCargo, cargo)

        cargo.funcaoProfissional = "Médico"

        val registosAlterados = tabelaCargo.update(
                cargo.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf(cargo.id.toString())
        )

        assertEquals(1, registosAlterados)
        val CargoBD = getCargoBD(tabelaCargo, cargo.id)
        assertEquals(cargo, CargoBD)

        db.close()
    }

}