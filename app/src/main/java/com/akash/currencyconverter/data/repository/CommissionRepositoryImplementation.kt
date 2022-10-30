package com.akash.currencyconverter.data.repository

import android.util.Log
import com.akash.currencyconverter.architecture.manager.SessionManager
import com.akash.currencyconverter.domain.repository.CommissionRepository
import com.akash.currencyconverter.round

class CommissionRepositoryImplementation(
    val sessionManager: SessionManager
)
    :CommissionRepository {

    override fun calculateCommission(currency: String, amount: Double): Double {
        Log.d("check", "calculateCommission: " + sessionManager.getConversionCount())
        return if(sessionManager.getConversionCount() < 5)
           (( amount * 0.7)/100).round(2)
        else
            0.0
    }

    override fun deductCommission() {
        sessionManager.updateConversionCount()
    }


}