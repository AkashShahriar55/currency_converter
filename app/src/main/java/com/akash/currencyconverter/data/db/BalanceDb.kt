package com.akash.currencyconverter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.akash.currencyconverter.BALANCE_DATA_FILENAME
import com.akash.currencyconverter.data.dao.BalanceDao
import com.akash.currencyconverter.data.worker.PrepopulateDbWorker
import com.akash.currencyconverter.data.worker.PrepopulateDbWorker.Companion.KEY_FILENAME
import com.akash.currencyconverter.domain.model.Balance

@Database(
    entities = [
        Balance::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BalanceDb:RoomDatabase() {
    abstract val balanceDao:BalanceDao
    companion object{

        @Volatile
        private var INSTANCE: BalanceDb? = null

        fun getInstance(context: Context): BalanceDb {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BalanceDb::class.java,
                        "currency_converter_database"
                    )
                        .addCallback(DatabaseCreationCallback(context))
                        .fallbackToDestructiveMigration() // if migrate the data will be lost . need to implement differently
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

        private class DatabaseCreationCallback(val context: Context): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val prepopulateRequest = OneTimeWorkRequestBuilder<PrepopulateDbWorker>()
                    .setInputData(workDataOf(KEY_FILENAME to BALANCE_DATA_FILENAME))
                    .build();
                WorkManager.getInstance(context).enqueue(prepopulateRequest)

            }
        }
    }
}