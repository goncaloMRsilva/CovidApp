package pt.ipg.covidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovaVacinaFragment : Fragment(){


    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextQnt: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_ver_vacinas

        return inflater.inflate(R.layout.fragment_nova_vacina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeVacina = view.findViewById(R.id.editTextNomeVacina)
        editTextQnt = view.findViewById(R.id.editTextQnt)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_NovaVacinaFragment_to_VerVacinasFragment)
    }

    fun guardar() {
        val nomeVacina = editTextNomeVacina.text.toString()
        if (nomeVacina.isEmpty()) {
            editTextNomeVacina.setError(getString(R.string.preencha_nome_vacina))
            editTextNomeVacina.requestFocus()
            return
        }


        val qnt = editTextQnt.text.toString().toInt()
        if (qnt==0) {
            editTextQnt.setError(getString(R.string.preencha_quantidade))
            editTextQnt.requestFocus()
            return
        }

        val vacina = Vacina(nome = nomeVacina, quantidade = qnt )

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovidApp.ENDERECO_VACINAS,
            vacina.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeVacina,
                getString(R.string.erro_inserir_vacina),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.vacina_guardada_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaVacinas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_vacina -> guardar()
            R.id.action_cancelar_nova_vacina -> navegaListaVacinas()
            else -> return false
        }

        return true
    }

}