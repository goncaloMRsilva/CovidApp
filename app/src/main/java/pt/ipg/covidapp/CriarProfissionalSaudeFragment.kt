package pt.ipg.covidapp

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CriarProfissionalSaudeFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {


    private lateinit var editTextNomeP: EditText
    private lateinit var spinnerCargos: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_criar_profissionalsaude

        return inflater.inflate(R.layout.fragment_criar_profissional_saude, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeP = view.findViewById(R.id.editTextNomeP)
        spinnerCargos = view.findViewById(R.id.spinnerCargos)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_CARGO, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun navegaCriarProfissionalSaude() {
        findNavController().navigate(R.id.action_CriarProfissionalSaudeFragment_to_ProfissionalSaudeFragment)
    }

    fun guardar() {
        val nomeP = editTextNomeP.text.toString()
        if (nomeP.isEmpty()) {
            editTextNomeP.setError(getString(R.string.preencha_nome))
            editTextNomeP.requestFocus()
            return
        }


        val IdCargo = spinnerCargos.selectedItemId

        val ProfissionalSaude = ProfissionalSaude(NomeProfissional = nomeP, IDCargo = IdCargo)

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovidApp.ENDERECO_CARGO,
            ProfissionalSaude.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeP,
                getString(R.string.erro_inserir_nome),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.cargo_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaCriarProfissionalSaude()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_profissionalsaude -> guardar()
            R.id.action_cancelar_novo_profissionalsaude -> navegaCriarProfissionalSaude()
            else -> return false
        }

        return true
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderCovidApp.ENDERECO_CARGO,
            TabelaCargo.TODOS_CAMPOS,
            null, null,
            TabelaCargo.CAMPO_FUNCAO
        )
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerCargo(data)
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerCargo(null)
    }

    private fun atualizaSpinnerCargo(data: Cursor?) {
        spinnerCargos.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaCargo.CAMPO_FUNCAO),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_CARGO = 0
    }
}