package com.example.vamorachar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var priceEditText: EditText
    private lateinit var peopleEditText: EditText
    private lateinit var totalTextView: TextView
    private lateinit var shareButton: FloatingActionButton
    lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        priceEditText = findViewById(R.id.preco)
        peopleEditText = findViewById(R.id.pessoas)
        totalTextView = findViewById(R.id.total)

        // Add text change listeners to the EditText fields
        priceEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateTotal()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        peopleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateTotal()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        shareButton = findViewById(R.id.share)
        totalTextView = findViewById(R.id.total)

        shareButton.setOnClickListener {
            if(peopleEditText.text.toString().toDoubleOrNull() ?: 1.0 >= 1.0){
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT,"O valor de " + priceEditText.text.toString() + ", dividido entre "+ peopleEditText.text.toString() +", ficou para cada um: "+ totalTextView.text.toString())
                startActivity(Intent.createChooser(shareIntent, "Share Total"))
            }

        }
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                // Set language of TextToSpeech
                textToSpeech.language = Locale.forLanguageTag("PT-BR")
            }
        }
        val listenButton: FloatingActionButton = findViewById(R.id.listen)
        listenButton.setOnClickListener {
            val text = totalTextView.text.toString()
            if (text != "Total por Pessoa"){
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }

        }



    }

    private fun updateTotal() {
        val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
        val people = peopleEditText.text.toString().toDoubleOrNull() ?: 1.0
        var total = 0.0
        if(people > 0){
            total = price / people
        }

        totalTextView.text = total.toString()
    }
}