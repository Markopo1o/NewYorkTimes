package hu.jm.newyorktimes.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news_table")
data class News(
    val pic: Bitmap,
    val title: String,
    val author: String,
    val updated: String,
    val url: String
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}