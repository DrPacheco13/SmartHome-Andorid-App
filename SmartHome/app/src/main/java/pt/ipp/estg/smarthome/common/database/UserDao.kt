package pt.ipp.estg.smarthome.common.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    /**
     * Adds a new user to the DB
     *
     * @param user details to add to the DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /**
     * Updates the login status to indicate if the user executed the login already
     *
     * @param logIn new login status
     * @param email the email of the user
     */
    @Query("UPDATE users SET login=:logIn WHERE email= :email")
    suspend fun updateLoggInStatus(logIn: Boolean, email: String)

    /**
     * Finds the user indicated by the email
     *
     * @param email of the user to be searched
     * @return the info of the user with the email indicated
     */
    @Query("SELECT * FROM users WHERE email = :email")
    fun findUserEmail(email: String): LiveData<User>

    /**
     * Finds the user indicated by the id
     *
     * @param id of the user to be searched
     * @return the info of the user with the id indicated
     */
    @Query("SELECT * FROM users WHERE id = :id")
    fun findUserId(id: Int): LiveData<User>

    /**
     * Finds all the users in the local DB
     *
     * @return all users in local DB
     */
    @Query("SELECT * FROM users")
    fun findAllUsers(): LiveData<List<User>>

    /**
     * Deletes the user indicated by the email
     *
     * @param email of the user intended to eliminate
     */
    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserEmail(email: String)

    /**
     * Deletes the user indicated by the id
     *
     * @param id of the user intended to eliminate
     */
    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUserId(id: Int)

}