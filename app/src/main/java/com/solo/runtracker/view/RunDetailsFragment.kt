package com.solo.runtracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.solo.runtracker.R
import com.solo.runtracker.database.Run
import com.solo.runtracker.database.RunDatabase
import com.solo.runtracker.viewmodel.RunDetailsViewModel
import com.solo.runtracker.viewmodel.RunDetailsViewModelFactory
import kotlinx.android.synthetic.main.fragment_run_details.*

class RunDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_run_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = RunDatabase.getInstance(application).RunDatabaseDao
        val viewModelFactory =
            RunDetailsViewModelFactory(
                dataSource,
                application
            )

        // Get a reference to the ViewModel associated with this fragment.
        val runDetailsViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(RunDetailsViewModel::class.java)


        //set up recycler view and adapter
        val adapter = RunAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        //observe livedata
        runDetailsViewModel.getAllRuns().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it as List<Run>
            }
        })

    }

}