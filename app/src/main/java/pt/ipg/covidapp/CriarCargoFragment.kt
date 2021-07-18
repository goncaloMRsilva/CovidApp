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
class CriarCargoFragment : Fragment(){

    private lateinit var editTextNome: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_cargo

        return inflater.inflate(R.layout.fragment_criar_cargo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun navegaListaProfissionalSaude() {
        findNavController().navigate(R.id.action_novo_Utente_Fragment_to_SecondFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }



        val uri = activity?.contentResolver?.insert(
            ContentProviderCovidApp.ENDERECO_CARGO,
            cargo.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_cargo),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.novo_cargo_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaProfissionalSaude()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_cargo -> guardar()
            R.id.action_cancelar_novo_cargo -> navegaListaProfissionalSaude()
            else -> return false
        }

        return true
    }

}