package br.com.authentication.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.authentication.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account = Firebase.auth.currentUser

        if(account != null){
            binding.apply {
                name.text = account.displayName
                mail. text = account.email
            }
        }

        listener()
    }

    private fun listener(){
        binding.btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}