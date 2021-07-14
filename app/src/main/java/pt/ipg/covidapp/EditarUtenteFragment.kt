package pt.ipg.covidapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditarUtenteFragment : Fragment(){



    private lateinit var editTextNome: EditText
    private lateinit var calendarViewDataNascimento: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_editar_utente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_utente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        calendarViewDataNascimento = view.findViewById(R.id.calendarViewDataNascimento)

        editTextNome.setText(DadosApp.utenteSelecionado!!.nomeUtente)
        calendarViewDataNascimento.setDate(DadosApp.utenteSelecionado!!.dataNascimento.time)
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_Editar_Utente_Fragment_to_Lista_Utente_Fragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }


        val dataNascimento = calendarViewDataNascimento.date
        /* if (dataNascimento == 0) {
             editTextDiaNascimento.setError(getString(R.string.preencha_data_nascimento))
             editTextDiaNascimento.requestFocus()
             return
         }*/

        val utente = DadosApp.utenteSelecionado!!
        utente.nomeUtente = nome
        utente.dataNascimento = Date(dataNascimento)

        val uriUtente = Uri.withAppendedPath(
            ContentProviderCovidApp.ENDERECO_UTENTES,
            utente.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriUtente,
            utente.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_utente,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.utente_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaUtentes()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_utente -> guardar()
            R.id.action_cancelar_edita_utente -> navegaListaUtentes()
            else -> return false
        }

        return true
    }

}