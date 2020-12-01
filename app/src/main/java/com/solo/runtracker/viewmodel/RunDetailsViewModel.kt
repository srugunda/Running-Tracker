package com.solo.runtracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.solo.runtracker.database.Run
import com.solo.runtracker.database.RunDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RunDetailsViewModel(val database: RunDatabaseDao, application: Application) : AndroidViewModel(application){

    fun getAllRuns(): LiveData<List<Run?>> {
        return database.getAllRuns()
    }

    fun clearAllData(){
        viewModelScope.launch {
            database.clear()
        }
    }

}
