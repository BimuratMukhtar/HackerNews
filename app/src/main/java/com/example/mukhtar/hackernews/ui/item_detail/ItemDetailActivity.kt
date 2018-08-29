package com.example.mukhtar.hackernews.ui.item_detail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mukhtar.hackernews.R
import kotlinx.android.synthetic.main.activity_item_detail.*


class ItemDetailActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        webView!!.settings.javaScriptEnabled = true
        webView!!.loadUrl(intent.getStringExtra(ARG_URL))
    }

    companion object {
        val ARG_URL = "URL"
        fun open(context: Context, url: String){
            val intent = Intent(context, ItemDetailActivity::class.java)
            intent.putExtra(ARG_URL, url)
            context.startActivity(intent)
        }
    }
}
