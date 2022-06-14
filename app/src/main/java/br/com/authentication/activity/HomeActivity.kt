package br.com.authentication.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import br.com.authentication.R
import br.com.authentication.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logOut -> {
                    logOut()
                    true
                }
                else -> false
            }
        }

        val account = Firebase.auth.currentUser

        if (account != null) {
            binding.apply {
                info.tvInfoName.text = account.displayName
                info.tvInfoEmail.text = account.email
                info.tvInfoTell.text = account.phoneNumber
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}