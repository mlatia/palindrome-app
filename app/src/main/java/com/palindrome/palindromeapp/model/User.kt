package com.palindrome.palindromeapp.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val email: String,
    @SerializedName("first_name")
    val firstname: String,
    @SerializedName("last_name")
    val lastname: String,
    val avatar: String
) : Parcelable {
    val fullName: String
        get() = "$firstname $lastname"
}

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)