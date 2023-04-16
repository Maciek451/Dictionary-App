package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DictEntryDao {

    @Insert
    suspend fun insertDictEntry(dictEntry: DictEntry)

    @Delete
    suspend fun deleteDictEntry(dictEntry: DictEntry)

    @Query("DELETE FROM words")
    suspend fun deleteAllEntries()

    @Query("SELECT * FROM words")
    fun getAllEntries(): LiveData<List<DictEntry>>
}