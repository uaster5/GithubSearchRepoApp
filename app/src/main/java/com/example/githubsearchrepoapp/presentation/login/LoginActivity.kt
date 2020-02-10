package com.example.githubsearchrepoapp.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.net.Uri
import android.webkit.WebViewClient
import com.example.githubsearchrepoapp.presentation.search.SearchActivity


class LoginActivity : AppCompatActivity() {


    private val clientId = "Iv1.54a7164a53c95452"


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.githubsearchrepoapp.R.layout.activity_main)
        val uri = Uri.Builder()
            .scheme("https")
            .authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", "user,user:email")
            .build()
        webView.loadUrl(uri.toString())
        webView.clearCache(true)
        webView.clearHistory()
        webView.settings.javaScriptEnabled = true; // to perform authorize functions
        webView.settings.javaScriptCanOpenWindowsAutomatically = true;
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val uri = Uri.parse(request?.url.toString())
                if (uri.getQueryParameter("code") != null
                    && uri.scheme != null
                    && uri.scheme!!.equals("https", ignoreCase = true)
                ) {

                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

    }
}
