package com.akash.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.currencyconverter.R
import com.akash.currencyconverter.architecture.viewmodels.CurrencyExchangeViewModel
import com.akash.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    val mainViewModel:CurrencyExchangeViewModel by viewModels()

    val balancesAdapter = BalancesAdapter()

    var sellCurrency:String? = null
    var buyCurrency:String? = null


    var alreadyValidated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etSellingAmount.isEnabled = false

        edgeToEdge {
            binding.appbar.fit { Edge.Top }
        }

        binding.rvBalance.layoutManager = LinearLayoutManager(this)
        binding.rvBalance.adapter = balancesAdapter

        mainViewModel.fetchUserBalances().observe(this){
            val curency = it.map { balance -> balance.currency }
            val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,curency);
            binding.sellCurrency.adapter = adapter
            adapter.notifyDataSetChanged()


            balancesAdapter.submitList(it)

            binding.etSellingAmount.isEnabled = true


        }


        mainViewModel.availableExchanges.observe(this){
            val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,it);
            binding.buyCurrency.adapter = adapter
            adapter.notifyDataSetChanged()


        }


        binding.buyCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buyCurrency = binding.buyCurrency.selectedItem as String?

                if(binding.etSellingAmount.text.isNotEmpty()){
                    val convertedValue = mainViewModel.convertAmount(sellCurrency!!,buyCurrency,binding.etSellingAmount.text.toString())
                    binding.etBuyAmount.text = "+$convertedValue"
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("spinner", "onCreate: " + binding.sellCurrency.selectedItem + " " + binding.buyCurrency.selectedItem)
            }

        }


        binding.sellCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sellCurrency = binding.sellCurrency.selectedItem as String?

                if(binding.etSellingAmount.text.isNotEmpty()){
                    val convertedValue = mainViewModel.convertAmount(sellCurrency!!,buyCurrency,binding.etSellingAmount.text.toString())
                    binding.etBuyAmount.text = "+$convertedValue"
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }








        binding.etSellingAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("text", "beforeTextChanged: " + s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("text", "onTextChanged: " + s)
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("text", "afterTextChanged: " + s?.toString())

                s?.let { editable->
                    if(editable.isEmpty()){
                        binding.etBuyAmount.text = "+0.0"
                        return
                    }

                    binding.etSellingAmount.removeTextChangedListener(this)
                    val actualValue = mainViewModel.validateValue(sellCurrency!!,editable.toString())
                    editable.replace(0,editable.length,actualValue)
                    binding.etSellingAmount.addTextChangedListener(this)


                    val convertedValue = mainViewModel.convertAmount(sellCurrency!!,buyCurrency,actualValue)
                    binding.etBuyAmount.text = "+$convertedValue"

                }


            }

        })




    }
}