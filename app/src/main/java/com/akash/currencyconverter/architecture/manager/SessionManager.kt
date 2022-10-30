package com.akash.currencyconverter.architecture.manager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.akash.currencyconverter.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)



    companion object {
        const val CONVERSION_COUNT = "conversion_count"
    }

    /**
     * Function to update conversion count
     */
    fun getConversionCount(): Int {
        return prefs.getInt(CONVERSION_COUNT, 0)
    }

    fun updateConversionCount() {
        val editor = prefs.edit()
        editor.putInt(CONVERSION_COUNT, getConversionCount()+1)
        editor.apply()
    }



}