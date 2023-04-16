package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntry
import uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.DictEntryDao

@Database(entities = [DictEntry::class], version = 1)
abstract class AssignmentCS31620RoomDatabase : RoomDatabase() {
    abstract fun dictEntryDao(): DictEntryDao

    companion object {
        private var instance: AssignmentCS31620RoomDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AssignmentCS31620RoomDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder<AssignmentCS31620RoomDatabase>(
                        context.applicationContext,
                        AssignmentCS31620RoomDatabase::class.java,
                        "assignment_database"
                    )
                        .build()
            }
            return instance
        }
    }
}