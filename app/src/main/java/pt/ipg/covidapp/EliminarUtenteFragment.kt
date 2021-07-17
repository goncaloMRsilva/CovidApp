package pt.ipg.covidapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [ApagaUtenteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminarUtenteFragment : Fragment(){

    private lateinit var textViewNome: TextView
    private lateinit var textViewDoses: TextView
    private lateinit var textViewDataNascimento: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_eliminar_utente

        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eliminar_utente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewNomeApaga)
        textViewDoses = view.findViewById(R.id.textViewDosesApaga)
        textViewDataNascimento = view.findViewById(R.id.textViewDataNascimentoApaga)

        val utente = DadosApp.utenteSelecionado!!
        textViewNome.setText(utente.nomeUtente)
        textViewDoses.setText(utente.dose.toString())
        textViewDataNascimento.setText("${utente.dataNascimento.day}/${utente.dataNascimento.month+1}/${utente.dataNascimento.year+1900}")
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_eliminarUtenteFragment_to_Lista_Utente_Fragment)
    }

    fun apaga() {
        val uriUtente = Uri.withAppendedPath(
            ContentProviderCovidApp.ENDERECO_UTENTES,
            DadosApp.utenteSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriUtente,
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
            R.string.utente_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaUtentes()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_apagar_utente -> apaga()
            R.id.action_cancelar_apagar_utente -> navegaListaUtentes()
            else -> return false
        }

        return true
    }
}