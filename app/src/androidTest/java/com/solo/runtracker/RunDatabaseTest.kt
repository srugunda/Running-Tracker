package com.solo.runtracker

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.solo.runtracker.database.Run
import com.solo.runtracker.database.RunDatabase
import com.solo.runtracker.database.RunDatabaseDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RunDatabaseTest {

    private lateinit var runDao: RunDatabaseDao
    private lateinit var db: RunDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, RunDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        runDao = db.RunDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetRun() {
        val run = Run()
        runDao.insert(run)
        val lastRun = runDao.getLastRun()
        Assert.assertEquals(lastRun?.runFeedback, -1)
    }
}
