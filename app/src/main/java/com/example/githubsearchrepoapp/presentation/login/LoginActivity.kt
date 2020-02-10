package com.example.githubsearchrepoapp.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubsearchrepoapp.R
import com.example.githubsearchrepoapp.presentation.search.SearchActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, SearchActivity::class.java))
    }
}
