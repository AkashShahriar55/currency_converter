package com.akash.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akash.currencyconverter.R
import com.akash.currencyconverter.databinding.ActivityMainBinding
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edgeToEdge {
            binding.appbar.fit { Edge.Top }
        }
    }
}