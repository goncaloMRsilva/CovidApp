package pt.ipg.covidapp

class DadosApp {
    companion object {
        lateinit var activity: MainActivity

        lateinit var listaDosesFragment: ListaDosesFragment
        var dosesSelecionado : Dosagem? = null

        lateinit var listaUtentesFragment: ListaUtentesFragment
        var utenteSelecionado : Utente? = null
    }
}