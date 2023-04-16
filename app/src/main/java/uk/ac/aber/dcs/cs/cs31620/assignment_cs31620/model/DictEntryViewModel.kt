package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.datasource.AssignmentCS31620Repository

class DictEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AssignmentCS31620Repository = AssignmentCS31620Repository(application)

    var dictEntries: LiveData<List<DictEntry>> = repository.getAllEntries()
        private set

    fun insertDictEntry(dictEntry: DictEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(dictEntry)
        }
    }

    fun deleteAllEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun deleteDictEntry(dictEntry: DictEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(dictEntry)
        }
    }
}