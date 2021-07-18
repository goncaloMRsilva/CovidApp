package pt.ipg.covidapp

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity

        lateinit var fragment: Fragment
        var dosesSelecionado : Dosagem? = null

        var utenteSelecionado : Utente? = null

        var vacinaSelecionada : Vacina? = null

        var profissionalSaudeSelecionado : ProfissionalSaude? = null
    }
}