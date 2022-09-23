package com.example.taskapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskapp.data.remote.responses.ShortUrlResponse

@Entity(tableName = "short_urls")
data class ShortURL(
    val full_short_link: String,
    val full_short_link2: String,
    val full_share_link: String,
    val original_link: String,
    var isCopied: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)

fun ShortUrlResponse.toShortUrl(): ShortURL {
    return ShortURL(
        full_share_link = this.result?.shareLink ?: "",
        full_short_link2 = this.result?.fullShortLink2 ?: "",
        full_short_link = this.result?.fullShortLink ?: "",
        original_link = this.result?.originalLink ?: "",
    )
}