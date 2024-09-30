package com.example.firebaserealtimedatabase

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaserealtimedatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DataAdapter.ItemCLickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataAdapter: DataAdapter
    private val viewModel: DataViewModel by viewModels()
    private var editingData: Data? = null  // Variable to keep track of the item being edited

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Observe LiveData from ViewModel
        viewModel.fetchData().observe(this, Observer { dataList ->
            dataAdapter.updateData(dataList ?: emptyList())
        })

        binding.saveBtn.setOnClickListener {
            saveData()
        }
    }

    private fun setupRecyclerView() {
        dataAdapter = DataAdapter(emptyList(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = dataAdapter
    }

    private fun saveData() {
        val studId = binding.idEtxt.text.toString()
        val name = binding.nameEtxt.text.toString()
        val email = binding.emailEtxt.text.toString()
        val subject = binding.subjectEtxt.text.toString()
        val birthdate = binding.birthDateEtxt.text.toString()

        if (studId.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && birthdate.isNotEmpty()) {
            val data = Data(studid = studId, name = name, email = email, subject = subject, birthdate = birthdate)

            if (editingData == null) {  // If no data is being edited, add new data
                viewModel.addData(data)
            } else {  // If editing, update the existing data
                viewModel.updateData(editingData!!.copy(studid = studId, name = name, email = email, subject = subject, birthdate = birthdate))
                editingData = null // Reset editing state
            }

            clearFields()
        }
    }

    private fun clearFields() {
        binding.idEtxt.text.clear()
        binding.nameEtxt.text.clear()
        binding.emailEtxt.text.clear()
        binding.subjectEtxt.text.clear()
        binding.birthDateEtxt.text.clear()
    }

    override fun onEditItemClick(data: Data) {
        // Populate the fields with the current data
        binding.idEtxt.setText(data.studid)
        binding.nameEtxt.setText(data.name)
        binding.emailEtxt.setText(data.email)
        binding.subjectEtxt.setText(data.subject)
        binding.birthDateEtxt.setText(data.birthdate)

        // Set the current data as the editing data
        editingData = data
    }

    override fun onDeleteItem(data: Data) {
        viewModel.deleteData(data)
    }
}