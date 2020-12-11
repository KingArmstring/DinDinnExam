package com.kingarmstring.dindinnexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
/*
My plan for the repository is to create rxjava objects and make them wrap ApiResponse objects and
return them to the viewModel while setting the state.
 */