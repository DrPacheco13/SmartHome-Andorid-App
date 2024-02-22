package pt.ipp.estg.smarthome.common.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val repository: UserRepository

    /**
     * Finds all the users in the local DB
     *
     * @return all users in local DB
     */
    val allUsers: LiveData<List<User>>

    init {
        val userDb = UserRoomDatabase.getDatabase(application)
        repository = UserRepository(userDb.getUserDao())

        allUsers = repository.findAllUsers()
    }

    /**
     * Adds a new user to the DB
     *
     * @param user details to add to the DB
     */
    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    /**
     * Updates the login status to indicate if the user executed the login already
     *
     * @param logIn new login status
     * @param email the email of the user
     */
    fun updateLoggInStatus(logIn: Boolean, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLoggInStatus(logIn, email)
        }
    }

    /**
     * Finds the user indicated by the email
     *
     * @param email of the user to be searched
     * @return the info of the user with the email indicated
     */
    fun findUserEmail(email: String): LiveData<User> {
        return repository.findUserEmail(email)
    }

    /**
     * Finds the user indicated by the id
     *
     * @param id of the user to be searched
     * @return the info of the user with the id indicated
     */
    fun findUserId(id: Int): LiveData<User> {
        return repository.findUserId(id)
    }

    /**
     * Finds the user indicated by the id
     *
     * @return the info of all users
     */
    fun findAllUsers(): LiveData<List<User>> {
        return repository.findAllUsers()
    }

    /**
     * Deletes the user indicated by the email
     *
     * @param email of the user intended to eliminate
     */
    fun deleteUserEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserEmail(email)
        }
    }

    /**
     * Deletes the user indicated by the id
     *
     * @param id of the user intended to eliminate
     */
    fun deleteUserId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserId(id)
        }
    }
}