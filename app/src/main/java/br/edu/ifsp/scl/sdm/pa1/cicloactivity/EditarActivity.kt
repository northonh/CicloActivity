package br.edu.ifsp.scl.sdm.pa1.cicloactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.cicloactivity.databinding.ActivityEditarBinding

class EditarActivity : AppCompatActivity() {
    private lateinit var activityEditarBinding: ActivityEditarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditarBinding = ActivityEditarBinding.inflate(layoutInflater)
        setContentView(activityEditarBinding.root)

        // ActionBar
        supportActionBar?.title = "Editar"

        activityEditarBinding.salvarBt.setOnClickListener {
            val retornoIntent: Intent = Intent()
            with (activityEditarBinding) {
                retornoIntent.putExtra(MainActivity.NOME, nomeEt.text.toString())
                retornoIntent.putExtra(MainActivity.SOBRENOME, sobrenomeEt.text.toString())
            }
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}