package com.sdmdevelopers.asturspot.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.sdmdevelopers.asturspot.R

class AuthActivity : AppCompatActivity() {

    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button //boton iniciar sesion
    private lateinit var signUpButton: Button //boton registrarse
    private lateinit var guestButton: Button //boton invitado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAuth.getInstance().signOut()  // Cierra la sesión actual
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        compruebaLogin()
        setUp()
    }

    //Comprobamos si el usuario ya está logeado,y, en caso de que ya lo esté, iniciamos sesión automáticamente
    private fun compruebaLogin() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null){
            showHome(currentUser.email ?: "")
        }
    }

    private fun setUp() {
        emailEditText = requireViewById(R.id.emailLogin)
        passwordEditText = requireViewById(R.id.passwordLogin)
        loginButton = requireViewById(R.id.loginButton)
        signUpButton = requireViewById(R.id.signUpButton)
        guestButton = requireViewById(R.id.guestButton)

        //boton de iniciar sesion
        loginButton.setOnClickListener {
            if (emailEditText.text.isNotBlank() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString())
                    .addOnCompleteListener { result -> //por defecto siempre habrá un parámetro de nombre is
                        if (result.isSuccessful) { //tuvo éxito
                            //abrimos la pagina principal de la app (el MainActivity)
                            showHome(emailEditText.text.toString())
                        } else { //se produjo un error
                            //abrimos un diálogo de error
                            showAlert()
                        }
                    }
            }
        }

        //boton de registrarse
        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotBlank() && passwordEditText.text.isNotEmpty()) {
                //Se lanza en un hilo aparte de forma transparente, por lo que la respuesta no tiene por qué ser inmediata
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString())
                    .addOnCompleteListener { result ->
                        if(result.isSuccessful) { //tuvo éxito
                            //abrimos la pagina principal de la app (el MainActivity)
                            showHome(emailEditText.text.toString())
                        } else { //se produjo un error
                            //abrimos un diálogo de error
                            showAlert()
                        }
                    }
            }
        }

        //Boton de entrar sin iniciar sesion
        guestButton.setOnClickListener {
            showHome("")
        }
    }

    //Metodo que crea un dialogo de error
    private fun showAlert() {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Creamos intent con MainActivity y le pasamos el email
    private fun showHome(email : String){
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(MainActivity.CLAVE_EMAIL, email)
        }
        startActivity(intent)
    }
}