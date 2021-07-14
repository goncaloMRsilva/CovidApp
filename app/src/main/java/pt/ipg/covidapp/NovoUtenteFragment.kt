package pt.ipg.covidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoUtenteFragment : Fragment(){

    private lateinit var editTextNome: EditText
    //
    private lateinit var calendarViewDataNascimento: CalendarView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_utente

        return inflater.inflate(R.layout.fragment_novo_utente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)

        calendarViewDataNascimento = view.findViewById(R.id.calendarViewDataNascimento)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_novo_Utente_Fragment_to_SecondFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }



        val dataNascimentoMillis = calendarViewDataNascimento.date
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dataNascimentoMillis
        //val dataNascimento
        /* if (dataNascimento == 0) {
             editTextDiaNascimento.setError(getString(R.string.preencha_data_nascimento))
             editTextDiaNascimento.requestFocus()
             return
         }*/

        val utente = Utente(nomeUtente = nome, dataNascimento = Date(dataNascimentoMillis))

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovidApp.ENDERECO_UTENTES,
            utente.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_utente),
                Snackbar.LENGTH_LONG
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
            R.id.action_guardar_novo_utente -> guardar()
            R.id.action_cancelar_novo_utente -> navegaListaUtentes()
            else -> return false
        }

        return true
    }

}