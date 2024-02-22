package pt.ipp.estg.smarthome.common.database

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    /**
     * Adds a new user to the DB
     *
     * @param user details to add to the DB
     */
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    /**
     * Updates the login status to indicate if the user executed the login already
     *
     * @param logIn new login status
     * @param email the email of the user
     */
    suspend fun updateLoggInStatus(logIn: Boolean, email: String){
        userDao.updateLoggInStatus(logIn,email)
    }

    /**
     * Finds the user indicated by the email
     *
     * @param email of the user to be searched
     * @return the info of the user with the email indicated
     */
    fun findUserEmail(email: String) : LiveData<User> {
        return userDao.findUserEmail(email)
    }

    /**
     * Finds the user indicated by the id
     *
     * @param id of the user to be searched
     * @return the info of the user with the id indicated
     */
    fun findUserId(id: Int) : LiveData<User> {
        return userDao.findUserId(id)
    }

    /**
     * Finds all the users in the local DB
     *
     * @return all users in local DB
     */
    fun findAllUsers() : LiveData<List<User>> {
        return userDao.findAllUsers()
    }

    /**
     * Deletes the user indicated by the email
     *
     * @param email of the user intended to eliminate
     */
    suspend fun deleteUserEmail(email: String) {
        userDao.deleteUserEmail(email)
    }

    /**
     * Deletes the user indicated by the id
     *
     * @param id of the user intended to eliminate
     */
    suspend fun deleteUserId(id: Int) {
        userDao.deleteUserId(id)
    }
}