package com.solo.runtracker.viewmodel

import android.app.Application
import android.os.SystemClock
import androidx.lifecycle.*
import com.solo.runtracker.database.Run
import com.solo.runtracker.database.RunDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RunTrackerViewModel(val database: RunDatabaseDao, application: Application) : AndroidViewModel(application){

    private var latestRun = MutableLiveData<Run?>()

    init {
        initializeLatestRun()
    }

    private fun initializeLatestRun() {
        viewModelScope.launch {
            latestRun.value = getLatestRunFromDatabase()
        }
    }

    fun getAllRuns(): LiveData<List<Run?>> {
       return database.getAllRuns()
    }

    fun getLastRun(): LiveData<Run?> {
        return database.getLastRun()
    }

    fun insertData(run: Run){
        viewModelScope.launch {
            //database.insert(run)
            insert(run)

            latestRun.value = getLatestRunFromDatabase()
        }
    }

    private suspend fun getLatestRunFromDatabase(): Run? {
        var run = database.getRecentRun()
        if (run?.endTimeMilli != run?.startTimeMilli) {
            run = null
        }
        return run
    }

    private suspend fun insert(run : Run) {
        database.insert(run)
    }

    fun updateData(elapsedTime : String, runFeedback : Int){
        viewModelScope.launch {
            //get current item from DB
            val currentRun = latestRun.value ?: return@launch
            //update the item
            currentRun.elapsedTime = elapsedTime
            currentRun.endTimeMilli = System.currentTimeMillis()
            currentRun.runFeedback = runFeedback
            //update the db with the item
            update(currentRun)
        }
    }

    private suspend fun update(run : Run) {
        database.update(run)
    }

}

