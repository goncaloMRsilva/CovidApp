package pt.ipg.covidapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MenuPrincipalFragment : Fragment(){

    private lateinit var buttonUtente: Button
    private lateinit var buttonDoses: Button
    private lateinit var buttonVacinas: Button
    private lateinit var buttonProfissionalSaude: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_menu_principal

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonUtente = view.findViewById(R.id.buttonUtente)
        buttonDoses = view.findViewById(R.id.buttonDoses)
        buttonVacinas = view.findViewById(R.id.buttonVacinas)
        buttonProfissionalSaude = view.findViewById(R.id.buttonProfissionalSaude)

        buttonUtente.setOnClickListener {
            navegaListaUtentes()
        }
        buttonDoses.setOnClickListener {
            navegaListaDoses()
        }
        buttonVacinas.setOnClickListener {
            navegaListaVacinas()
        }
        buttonProfissionalSaude.setOnClickListener {
            navegaProfissionalSaude()
        }
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_MenuPrincipalFragment_to_ListaUtenteFragment)
    }

    fun navegaListaDoses() {
        findNavController().navigate(R.id.action_MenuPrincipalFragment_to_Lista_Doses_Fragment)
    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_MenuPrincipalFragment_to_VerVacinasFragment)
    }

    fun navegaProfissionalSaude() {
        findNavController().navigate(R.id.action_MenuPrincipalFragment_to_ProfissionalSaudeFragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> return false
        }

        return true
    }

}