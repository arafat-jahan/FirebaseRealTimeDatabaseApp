package com.example.firebaserealtimedatabase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task

class DataViewModel : ViewModel() {

    private val dataRepository = DataRepository()

    fun fetchData(): MutableLiveData<List<Data>> {
        return dataRepository.fetchData()
    }

    fun addData(data: Data): Task<Void> {
        return dataRepository.addData(data)
    }

    fun deleteData(data: Data) {
        dataRepository.deleteData(data)
    }

    fun updateData(data: Data) {
        dataRepository.updateData(data)
    }
}
