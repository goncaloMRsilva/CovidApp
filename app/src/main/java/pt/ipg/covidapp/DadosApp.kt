package pt.ipg.covidapp

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var listaDosesFragment: ListaDosesFragment
        var dosesSelecionado : Dose? = null
    }
}