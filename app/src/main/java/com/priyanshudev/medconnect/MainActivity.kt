package com.priyanshudev.medconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.priyanshudev.common.domain.repository.MedConnectDataStore
import com.priyanshudev.doctor.DoctorActivity
import com.priyanshudev.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var medConnectDataStore: MedConnectDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Firebase.initialize(this)

        lifecycleScope.launch(Dispatchers.IO) {
            val isSignedIn = medConnectDataStore.getBoolean("SignedIn", false)
            withContext(Dispatchers.Main){
                if (isSignedIn){
                    val intent = Intent(this@MainActivity,DoctorActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this@MainActivity,LoginActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }
}