package com.example.datastore

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.datastore.utils.getThemePreference
import com.example.datastore.utils.saveThemePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.datastore.utils.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val themePreferenceFlow = getThemePreference(this)

        val userNameTextView: TextView = findViewById(R.id.userNameTextView)
        val userAgeTextView: TextView = findViewById(R.id.userAgeTextView)
        val themeTextView: TextView = findViewById(R.id.themeTextView)
        CoroutineScope(Dispatchers.IO).launch {
            themePreferenceFlow.collect { isDarkMode ->
                println("¿Modo oscuro activado? $isDarkMode")
            }
        }
    lifecycleScope.launch {
        saveUserName(this@MainActivity, "Juan Pérez")
        saveUserAge(this@MainActivity, 25)
        saveThemePreference(this@MainActivity, true) }


        // Leer datos de DataStore y mostrarlos
        lifecycleScope.launch {
            getUserName(this@MainActivity).collect { userName ->
                userNameTextView.text = "Nombre: $userName"
            }
        }

        lifecycleScope.launch {
            getUserAge(this@MainActivity).collect { userAge ->
                userAgeTextView.text = "Edad: $userAge"
            }
        }

        lifecycleScope.launch {
            getThemePreference(this@MainActivity).collect { isDarkMode ->
                themeTextView.text = if (isDarkMode) {
                    "Modo oscuro activado"
                } else {
                    "Modo oscuro desactivado"
                }
            }
        }
    }
}