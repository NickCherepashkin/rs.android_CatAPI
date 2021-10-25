package com.drozdova.catapi

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
	if (!isNetworkConnected()) {
		Toast.makeText(this,"Check your internet connection.", Toast.LENGTH_LONG).show()
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
	        return cm?.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected ?: false
	    }
}