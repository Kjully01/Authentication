package br.com.authentication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.authentication.R
import br.com.authentication.databinding.ActivityRegistrationBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()
    }

    private fun listener(){
        binding.btnRegistration.setOnClickListener{
            binding.run {
                if(etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty()){
                    Toast.makeText(this@RegistrationActivity, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                        .addOnCompleteListener { state ->
                            if(state.isSuccessful){
                                Toast.makeText(this@RegistrationActivity, "Usuário Cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                                etEmail.setText("")
                                etPassword.setText("")
                            }
                        }.addOnFailureListener { exception ->
                            lateinit var err: String
                            when(exception){
                                is FirebaseAuthWeakPasswordException -> {
                                    err = "Digite uma senha com pelo menos 6 digitos"
                                }
                                is FirebaseAuthInvalidCredentialsException -> {
                                    err = "Email inválido"
                                }
                                is FirebaseAuthUserCollisionException -> {
                                    err = "Email já cadastrado"
                                }
                                is FirebaseNetworkException -> {
                                    err = "Sem conexão com a internet"
                                }
                                else -> {
                                    err =  "Erro ao cadastrar usuário"
                                }
                            }
                            Toast.makeText(this@RegistrationActivity, err, Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}