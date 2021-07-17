package pt.ipg.covidapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var menu: Menu

    var menuAtual = R.menu.menu_lista_utentes
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu

        if(menuAtual == R.menu.menu_lista_utentes) {
            atualizaMenuListaUtentes(false)
        }else if (menuAtual == R.menu.menu_ver_vacinas){
            atualizaMenuVerVacinas(false)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }

            else -> when(menuAtual) {
                R.menu.menu_lista_utentes -> (DadosApp.fragment as ListaUtentesFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_utente -> (DadosApp.fragment as NovoUtenteFragment).processaOpcaoMenu(item)
                R.menu.menu_editar_utente -> (DadosApp.fragment as EditarUtenteFragment).processaOpcaoMenu(item)
                R.menu.menu_eliminar_utente -> (DadosApp.fragment as EliminarUtenteFragment).processaOpcaoMenu(item)
                R.menu.menu_ver_vacinas -> (DadosApp.fragment as VerVacinasFragment).processaOpcaoMenu(item)
                else -> false
            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaUtentes(mostraBotoesUtente : Boolean) {
        menu.findItem(R.id.action_editar_utente).setVisible(mostraBotoesUtente)
        menu.findItem(R.id.action_nova_dose).setVisible(mostraBotoesUtente)
        menu.findItem(R.id.action_apagar_utente).setVisible(mostraBotoesUtente)
    }

    fun atualizaMenuVerVacinas(mostraBotoesVacina : Boolean) {
        menu.findItem(R.id.action_editar_vacina).setVisible(mostraBotoesVacina)
        menu.findItem(R.id.action_alterar_quantidade).setVisible(mostraBotoesVacina)
    }
}