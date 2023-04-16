package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.datasource

import android.app.Application
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntry

class AssignmentCS31620Repository(application: Application) {
    private val dictEntryDao = AssignmentCS31620RoomDatabase.getDatabase(application)!!.dictEntryDao()

    suspend fun insert(dictEntry: DictEntry){
        dictEntryDao.insertDictEntry(dictEntry)
    }

    suspend fun delete(dictEntry: DictEntry){
        dictEntryDao.deleteDictEntry(dictEntry)
    }

    suspend fun deleteAll(){
        dictEntryDao.deleteAllEntries()
    }

    fun getAllEntries() = dictEntryDao.getAllEntries()
}