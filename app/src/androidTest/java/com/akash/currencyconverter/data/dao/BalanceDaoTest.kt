package com.akash.currencyconverter.data.dao

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.akash.currencyconverter.data.db.BalanceDb
import com.akash.currencyconverter.domain.model.Balance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BalanceDaoTest {
    lateinit var balanceDb: BalanceDb
    lateinit var context: Context
    lateinit var balanceDao: BalanceDao

    val balance1 = Balance("EUR",800.0)
    val balance2 = Balance("USD",200.0)
    val balance3 = Balance("BDT",100.0)

    //A JUnit Test Rule that swaps the background executor used by the Architecture Components with a different one which executes each task synchronously.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before(){
        context = InstrumentationRegistry.getInstrumentation().targetContext
        balanceDb = Room.inMemoryDatabaseBuilder(context,BalanceDb::class.java).build()
        balanceDao  = balanceDb.balanceDao

        balanceDao.insert(listOf(balance1,balance2))
    }

    @After
    fun after(){
        balanceDb.close()
    }



    @Test
    fun getAllBalance() = runBlocking{
        val allBalance = balanceDao.getAllBalance().first()
        Log.d("dao_test", "getAllBalance: " + allBalance)

        ViewMatchers.assertThat(allBalance.size, Matchers.equalTo(2))

        ViewMatchers.assertThat(allBalance[0], Matchers.equalTo(balance1))
        ViewMatchers.assertThat(allBalance[1], Matchers.equalTo(balance2))
    }

    @Test
    fun addBalance() = runBlocking{
        val result = balanceDao.insert(balance3)
        Log.d("result", "addFood: $result")
        val newFood = balanceDao.getBalanceById(result.toInt()).first()
        ViewMatchers.assertThat(newFood, Matchers.equalTo(balance3))

        balance1.id = 1
        val result1 = balanceDao.insert(balance1)
        Log.d("result", "addFood: $result1")
        ViewMatchers.assertThat(result1, Matchers.equalTo(-1))
    }


    @Test
    fun updateBalance(){
        balance3.id = 1
        val result = balanceDao.update(balance3)
        Log.d("result", "addFood: $result")
        ViewMatchers.assertThat(result, Matchers.equalTo(1))

        val newBalance1 = balance3.copy()
        val newBalance2 = balance3.copy()

        newBalance1.id = 1
        newBalance2.id = 2

        val result1 = balanceDao.update(listOf(newBalance1,newBalance2))
        Log.d("result", "addFood: $result1")
        ViewMatchers.assertThat(result1, Matchers.equalTo(2))
    }


    @Test
    fun deleteBalance() = runBlocking{
        balance3.id = 1
        val result = balanceDao.delete(balance3)
        val allBalances = balanceDao.getAllBalance().first()
        Log.d("result", "addFood: $result")
        ViewMatchers.assertThat(result, Matchers.equalTo(1))
        ViewMatchers.assertThat(allBalances.size, Matchers.equalTo(1))

        val result1 = balanceDao.delete(balance3)
        val allBalances1 = balanceDao.getAllBalance().first()
        Log.d("result", "addFood: $result1")
        ViewMatchers.assertThat(result1, Matchers.equalTo(0))
        ViewMatchers.assertThat(allBalances1.size, Matchers.equalTo(1))
    }
}