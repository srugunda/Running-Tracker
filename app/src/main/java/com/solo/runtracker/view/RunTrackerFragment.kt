package com.solo.runtracker.view

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.solo.runtracker.R
import com.solo.runtracker.database.Run
import com.solo.runtracker.database.RunDatabase
import com.solo.runtracker.getDate
import com.solo.runtracker.viewmodel.RunTrackerViewModel
import com.solo.runtracker.viewmodel.RunTrackerViewModelFactory
import kotlinx.android.synthetic.main.fragment_run_tracker.*


class RunTrackerFragment : Fragment() {

    var beginButtonPressed = false
    var runFeedback = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_run_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = RunDatabase.getInstance(application).RunDatabaseDao
        val viewModelFactory =
            RunTrackerViewModelFactory(
                dataSource,
                application
            )

        // Get a reference to the ViewModel associated with this fragment.
        val runTrackerViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(RunTrackerViewModel::class.java)


        //set up click listeners
        btn_begin.setOnClickListener {
            //Begin the chronometer
            timer.setBase(SystemClock.elapsedRealtime());
            timer.stop();
            timer.start()

            //Write the begin time to DB
            var currentRun = Run()
            runTrackerViewModel.insertData(currentRun)

            beginButtonPressed = true
        }

        btn_finish.setOnClickListener {
            timer.stop()
            val theElapsedTime = timer.text.toString()
            //show alert dialog to see how the run went
            showDialog()
            runTrackerViewModel.updateData(theElapsedTime, runFeedback)
        }

        btn_clear.setOnClickListener {
            tv_start_time.text = ""
            tv_end_time.text = ""
            tv_running_time.text = ""
            timer.setBase(SystemClock.elapsedRealtime());
            timer.stop();
        }


        //Observe Livedata
        runTrackerViewModel.getLastRun().observe(viewLifecycleOwner, Observer{

            if(beginButtonPressed){
                if (it!=null) {
                    if(it.elapsedTime.isEmpty()){
                        tv_start_time.text = "Start Time: ${getDate(it.startTimeMilli)}"
                    }
                    else{
                        tv_start_time.text = "Start Time: ${getDate(it.startTimeMilli)}"
                        tv_end_time.text = "End Time: ${getDate(it.endTimeMilli)}"
                        tv_running_time.text = "Running Time: ${it.elapsedTime}"
                    }
                }
            }
        })
    }

    private fun showDialog(){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder!!.setTitle("How was the run?")

        //performing positive action
        builder.setPositiveButton("It was great!"){dialogInterface, which ->
            Toast.makeText(context,"Feedback captured. Thanks",Toast.LENGTH_LONG).show()
            runFeedback=1
        }
        //performing cancel action
        builder.setNeutralButton("Cancel"){dialogInterface , which ->
           Toast.makeText(context,"Cancelled",Toast.LENGTH_LONG).show()
        }
        //performing negative action
        builder.setNegativeButton("Didn't Like It"){dialogInterface, which ->
            Toast.makeText(context,"Feedback captured. Thanks",Toast.LENGTH_LONG).show()
            runFeedback=0
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}