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
import pt.ipg.covidapp.databinding.FragmentNovoUtenteBinding
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoUtenteFragment : Fragment(){

    private var _binding: FragmentNovoUtenteBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextTelefone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var calendarViewDataNascimento: CalendarView


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_utente

        _binding = FragmentNovoUtenteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        editTextTelefone = view.findViewById(R.id.editTextTelefone)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextMorada = view.findViewById(R.id.editTextMorada)
        calendarViewDataNascimento = view.findViewById(R.id.calendarViewDataNascimento)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_NovoUtenteFragment_to_ListaUtentesFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }

        val telefone = editTextTelefone.text.toString()
        if (telefone.isEmpty()) {
            editTextTelefone.setError(getString(R.string.preencha_telefone))
            editTextTelefone.requestFocus()
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

        val utente = Utente(nome = nome, telefone = telefone, email = email, morada = morada, dataNascimento = Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)), dose = 0)

        val uri = activity?.contentResolver?.insert(
            ContentProviderArmazemVacinas.ENDERECO_UTENTES,
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