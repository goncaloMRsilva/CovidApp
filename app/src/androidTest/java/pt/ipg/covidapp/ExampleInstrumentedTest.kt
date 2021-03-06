package pt.ipg.covidapp

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdAdministracaoOpenHelper() = BdAdministracaoOpenHelper(getAppContext())

    private fun getTabelaUtentes(db: SQLiteDatabase) = TabelaUtente(db)
    private fun getTabelaVacinas(db: SQLiteDatabase) = TabelaVacinas(db)
    private fun getTabelaProfissionalSaude(db: SQLiteDatabase) = TabelaProfissionalSaude(db)
    private fun getTabelaCargo(db: SQLiteDatabase) = TabelaCargo(db)
    private fun getTabelaDosagem(db: SQLiteDatabase) = TabelaDosagem(db)


    private fun insereUtente(tabelaUtentes: TabelaUtente, utente: Utente): Long {
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

    private fun insereDosagem(tabelaDosagem: TabelaDosagem, dosagem: Dosagem): Long {
        val id = tabelaDosagem.insert(dosagem.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun getUtenteBD(
            tabelaUtentes: TabelaUtente, id: Long    ): Utente {
        val cursor = tabelaUtentes.query(
                TabelaUtente.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Utente.fromCursor(cursor)
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

    private fun getDosagemBD(
            tabelaDosagem: TabelaDosagem, id: Long  ): Dosagem {
        val cursor = tabelaDosagem.query(
                TabelaDosagem.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Dosagem.fromCursor(cursor)
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
        val utente = Utente(nomeUtente= "Goncalo Silva", dataNascimento = Date(1998-1900, 5, 12), dose = 2)

        utente.id = insereUtente(tabelaUtente, utente)
        val utenteBD = getUtenteBD(tabelaUtente, utente.id)
        assertEquals(utente, utenteBD)
        db.close()
    }

    @Test
    fun consegueAlterarUtentes(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaUtentes(db)
        val utente = Utente(nomeUtente= "?", dataNascimento = Date(1977-1900,2,9), dose = 2)

        utente.id = insereUtente(tabelaUtentes, utente)
        utente.nomeUtente= "Adriano Lameiras"
        utente.dataNascimento= Date(1998-1900, 3, 2)
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
        val utente = Utente(nomeUtente= "?", dataNascimento = Date(1980-1900,5,11), dose = 2)

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
        val utente = Utente(nomeUtente= "?", dataNascimento = Date(1978-1900,6,8), dose = 2)

        utente.id = insereUtente(tabelaUtentes, utente)

        val utenteBD = getUtenteBD(tabelaUtentes, utente.id)
        assertEquals(utente, utenteBD)

        db.close()
    }



    @Test
    fun consegueInserirProfissionalSaude(){

        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Auxiliar de saude")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira", IDCargo = cargo.id)

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)
        val ProfissionalSaudeBD = getProfissionalSaudeBD(tabelaProfissionalSaude, ProfissionalSaude.id)
        assertEquals(ProfissionalSaude, ProfissionalSaudeBD)
        db.close()
    }


    @Test
    fun consegueAlterarProfissionalSaude(){

        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Auxiliar de saude")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db);
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira", IDCargo = cargo.id)

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
        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Auxiliar de saude")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "?", IDCargo = cargo.id)

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
        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Auxiliar de saude")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira", IDCargo = cargo.id)

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

        cargo.funcaoProfissional = "M??dico"

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

    @Test
    fun consegueApagarCargo(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Enfermeiro")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val registosEliminados =tabelaCargo.delete(
                "${BaseColumns._ID}=?",
                arrayOf(cargo.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerCargo(){
        val db = getBdAdministracaoOpenHelper().writableDatabase
        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Auxiliar de saude")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val CargoBD = getCargoBD(tabelaCargo, cargo.id)
        assertEquals(cargo, CargoBD)

        db.close()
    }

    @Test
    fun consegueInserirDosagem(){
        val db = getBdAdministracaoOpenHelper().writableDatabase

        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Auxiliar de saude")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira", IDCargo = cargo.id)

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)


        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nomeUtente= "Goncalo Silva", dataNascimento = Date(1983-1900,4,21), dose = 2)

        utente.id = insereUtente(tabelaUtente, utente)


        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina( nome = "AstraZeneca", quantidade = 1000)

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaDosagem = getTabelaDosagem(db)
        val dosagem = Dosagem(DataAdministracao = Date(1972-1900,4,7), Dose = 1, IdUtente = utente.id, IdVacina = vacina.id, IdProfSaude = ProfissionalSaude.id)


        dosagem.id = insereDosagem(tabelaDosagem, dosagem)
        val dosagemBD = getDosagemBD(tabelaDosagem, dosagem.id)
        assertEquals(dosagem, dosagemBD)
        db.close()
    }

    @Test
    fun consegueAlterarDosagem(){
        val db = getBdAdministracaoOpenHelper().writableDatabase

        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Enfermeiro")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira", IDCargo = cargo.id)

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)


        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nomeUtente= "Goncalo Silva", dataNascimento = Date(1983-1900,5,13), dose = 2)

        utente.id = insereUtente(tabelaUtente, utente)


        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina( nome = "AstraZeneca", quantidade = 1000)

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val tabelaDosagem = getTabelaDosagem(db);
        val dosagem = Dosagem(DataAdministracao = Date(1987-1900,14,8), Dose = 0, IdUtente = utente.id, IdVacina = vacina.id, IdProfSaude = ProfissionalSaude.id)

        dosagem.id = insereDosagem(tabelaDosagem, dosagem)

        dosagem.DataAdministracao = Date(1972-1900,4,18)
        dosagem.Dose = 2
        dosagem.IdUtente = 1234566
        dosagem.IdVacina = 3660123
        dosagem.IdProfSaude = 754749

        val registosAlterados = tabelaDosagem.update(
                dosagem.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf(dosagem.id.toString())
        )

        assertEquals(1, registosAlterados)
        val DosagemBD = getDosagemBD(tabelaDosagem, dosagem.id)
        assertEquals(dosagem, DosagemBD)

        db.close()
    }

    @Test
    fun consegueApagarDosagem(){
        val db = getBdAdministracaoOpenHelper().writableDatabase

        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "M??dico")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Jos?? Soares", IDCargo = cargo.id)

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)


        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nomeUtente= "Goncalo Silva", dataNascimento = Date(1988-1990,7,9), dose = 2)

        utente.id = insereUtente(tabelaUtente, utente)


        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina( nome = "Moderna", quantidade = 1000)

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val tabelaDosagem = getTabelaDosagem(db)
        val dosagem = Dosagem(DataAdministracao = Date(1978-1900,11,11), Dose = 7, IdUtente = utente.id, IdVacina = vacina.id, IdProfSaude = ProfissionalSaude.id)

        dosagem.id = insereDosagem(tabelaDosagem, dosagem)

        val registosEliminados =tabelaDosagem.delete(
                "${BaseColumns._ID}=?",
                arrayOf(dosagem.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerDosagem(){
        val db = getBdAdministracaoOpenHelper().writableDatabase

        val tabelaCargo = getTabelaCargo(db)
        val cargo = Cargo(funcaoProfissional = "Auxiliar de saude")

        cargo.id = insereCargo(tabelaCargo, cargo)

        val tabelaProfissionalSaude = getTabelaProfissionalSaude(db)
        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = "Claudia Vieira", IDCargo = cargo.id)

        ProfissionalSaude.id = insereProfissionalSaude(tabelaProfissionalSaude, ProfissionalSaude)


        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nomeUtente= "Goncalo Silva", dataNascimento = Date(1967-1990,12,2), dose = 2)

        utente.id = insereUtente(tabelaUtente, utente)


        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina( nome = "AstraZeneca", quantidade = 1000)

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val tabelaDosagem = getTabelaDosagem(db)
        val dosagem = Dosagem(DataAdministracao = Date(1976-1900, 23,8), Dose = 4, IdUtente = utente.id, IdVacina = vacina.id, IdProfSaude = ProfissionalSaude.id)

        dosagem.id = insereDosagem(tabelaDosagem, dosagem)

        val DosagemBD = getDosagemBD(tabelaDosagem, dosagem.id)
        assertEquals(dosagem, DosagemBD)

        db.close()
    }

}