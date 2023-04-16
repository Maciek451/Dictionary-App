package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class DictEntry(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var word: String = "",
    var translation: String = "",
) {
}