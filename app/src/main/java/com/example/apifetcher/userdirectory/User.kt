package com.example.apifetcher.userdirectory

// ADD THESE IMPORTS
import androidx.room.Ignore
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,

    // These fields will be parsed by Gson from the network,
    // but @Ignore tells Room to NOT try and save them to the database.
    @Ignore
    val address: Address? = null,

    @Ignore
    val company: Company? = null
)

/**
 * New data class to match the nested "address" object in the JSON
 */
data class Address(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?
    // We can ignore the "geo" object for simplicity
)

/**
 * New data class to match the nested "company" object in the JSON
 */
data class Company(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
)