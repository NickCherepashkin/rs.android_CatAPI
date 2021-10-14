package com.drozdova.catapi

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
	if (isNetworkConnected()) {
	        } else {
	            Toast.makeText(
	                this,
	                "HELLO",
	                Toast.LENGTH_LONG
	            ).show()
	        }

	        if (savedInstanceState == null) {
	            supportFragmentManager.beginTransaction()
	                .replace(
	                    R.id.fragmentContainer,
	                    CatsListFragment()
	                )
	                .commit()
	        }
	    }
	private fun isNetworkConnected(): Boolean {
	        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
	        return cm!!.activeNetworkInfo != null && cm!!.activeNetworkInfo?.isConnected ?: false
	    }
}