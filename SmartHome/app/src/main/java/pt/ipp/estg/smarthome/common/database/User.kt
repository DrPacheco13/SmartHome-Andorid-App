package pt.ipp.estg.smarthome.common.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "password")
    var password: String = ""

    @NonNull
    @ColumnInfo(name = "login")
    var loggedIn: Boolean = false

    constructor() {}

    constructor(email: String, password: String, loggedIn: Boolean) {
        this.email = email
        this.password = password
        this.loggedIn = loggedIn
    }
}