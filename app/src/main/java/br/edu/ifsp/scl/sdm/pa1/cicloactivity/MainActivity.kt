package br.edu.ifsp.scl.sdm.pa1.cicloactivity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.pa1.cicloactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var nomeEt: EditText
    private lateinit var sobrenomeEt: EditText
    private lateinit var editarActivityResultLauncher: ActivityResultLauncher<Intent>

    companion object {
        val CICLO_ACTIVITY: String = "CICLO_ACTIVITY"
        val NOME = "NOME"
        val SOBRENOME = "SOBRENOME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // ActionBar
        supportActionBar?.title = "Principal"

        nomeEt = EditText(this)
        nomeEt.width = LinearLayout.LayoutParams.MATCH_PARENT
        nomeEt.height = LinearLayout.LayoutParams.WRAP_CONTENT
        nomeEt.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        nomeEt.hint = "Nome"
        activityMainBinding.root.addView(nomeEt)

        sobrenomeEt = EditText(this)
        with (sobrenomeEt) {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height = LinearLayout.LayoutParams.WRAP_CONTENT
            inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            hint = "Sobrenome"
            activityMainBinding.root.addView(this)
        }

        savedInstanceState?.getString(NOME).takeIf { it != null }.apply{ nomeEt.setText(this) }

        editarActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado?.resultCode == RESULT_OK){
                with (resultado) {
                    data?.getStringExtra(NOME).takeIf { it != null }.let { nomeEt.setText(it) }
                    data?.getStringExtra(SOBRENOME).takeIf { it != null }.run { sobrenomeEt.setText(this) }
                }
            }
        }

        Log.v(CICLO_ACTIVITY, "onCreate: Iniciando ciclo de vida COMPLETO")
    }

    override fun onStart() {
        super.onStart()
        Log.v(CICLO_ACTIVITY, "onStart: Iniciando ciclo de vida VISÍVEL")
    }

    override fun onResume() {
        super.onResume()
        Log.v(CICLO_ACTIVITY, "onResume: Iniciando ciclo de vida em PRIMEIRO PLANO")
    }

    override fun onPause() {
        super.onPause()
        Log.v(CICLO_ACTIVITY, "onPause: Finalizando ciclo de vida em PRIMEIRO PLANO")
    }

    override fun onStop() {
        super.onStop()
        Log.v(CICLO_ACTIVITY, "onStop: Finalizando ciclo de vida VISÍVEL")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(CICLO_ACTIVITY, "onDestroy: Finalizando ciclo de vida COMPLETO")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(CICLO_ACTIVITY, "onRestart: Preparando execução do onStart")
    }

    // Salvando dados de instância
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NOME, nomeEt.text.toString())
    }

    // Restaurando dados de instância
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //savedInstanceState.getString(NOME).takeIf { it != null }.apply{ nomeEt.setText(this) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.editarMi -> {
                val editarIntent: Intent = Intent(this, EditarActivity::class.java)
                editarActivityResultLauncher.launch(editarIntent)
                true
            }
            else -> {
                false
            }
        }
    }
}