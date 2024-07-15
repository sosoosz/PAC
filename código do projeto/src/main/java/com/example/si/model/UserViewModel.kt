package com.example.si.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val medicamentoDao = AppDatabase.getDatabase(application).medicamentoDao()
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState
    private val _deleteUserState = MutableStateFlow(false)
    val deleteUserState: StateFlow<Boolean> = _deleteUserState
    private val _currentUserId = MutableStateFlow<Int?>(null)
    val currentUserId: StateFlow<Int?> = _currentUserId
    private val _currentUserName = MutableStateFlow<String?>(null)
    val currentUserName: StateFlow<String?> = _currentUserName

    init {
        // Initialize the currentUserId from loginState
        _currentUserId.value = _loginState.value.userId
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            _currentUserId.value?.let { userId ->
                val user = userDao.getUserById(userId)
                _currentUserName.value = user?.name
            }
        }
    }

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginState(isLoggedIn = false, loginError = "Existem campos por preencher")
            return
        }
        viewModelScope.launch {
            val user = userDao.getUserByUsername(username)
            if (user != null && user.password == password) {
                _loginState.value = LoginState(isLoggedIn = true, userId = user.id, loginError = null)
                _currentUserId.value = user.id
                _currentUserName.value = user.name
            } else {
                _loginState.value = LoginState(isLoggedIn = false, loginError = "Credenciais Inválidas")
            }
        }
    }

    fun register(name: String, username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (password != confirmPassword) {
                _registerState.value = RegisterState(registerError = "Palavras-passe não coincidem")
            } else if (name.isBlank() || username.isBlank() || password.isBlank()) {
                _registerState.value = RegisterState(registerError = "Preencha todos os campos")
            } else {
                val existingUser = userDao.getUserByUsername(username)
                if (existingUser != null) {
                    _registerState.value = RegisterState(registerError = "Nome de Utilizador já existe")
                } else {
                    userDao.insert(User(username = username, password = password, name = name))
                    _registerState.value = RegisterState(isRegistered = true, registerError = null)
                }
            }
        }
    }

    fun updateUser(newName: String?, newUsername: String?, newPassword: String?) {
        val userId = _currentUserId.value ?: return
        viewModelScope.launch {
            val user = userDao.getUserById(userId)
            if (user != null) {
                val updatedUser = user.copy(
                    name = newName ?: user.name,
                    username = newUsername ?: user.username,
                    password = newPassword ?: user.password
                )
                userDao.update(updatedUser)
                _currentUserName.value = updatedUser.name
            }
        }
    }

    fun deleteUser() {
        val userId = _currentUserId.value ?: return
        viewModelScope.launch {
            val user = userDao.getUserById(userId)
            if (user != null) {
                userDao.delete(user)
                _loginState.value = LoginState(isLoggedIn = false, userId = null)
                _currentUserId.value = null
                _currentUserName.value = null
                _deleteUserState.value = true
            }
        }
    }

    fun logout() {
        _loginState.value = LoginState(isLoggedIn = false, userId = null)
        _currentUserId.value = null
        _currentUserName.value = null
    }

    fun resetDeleteUserState() {
        _deleteUserState.value = false
    }

    fun resetLoginState() {
        _loginState.value = LoginState(isLoggedIn = false, userId = null, loginError = null)
        _currentUserId.value = null
        _currentUserName.value = null
    }

    fun resetRegisterState() {
        _registerState.value = RegisterState(isRegistered = false, registerError = null)
    }

    fun clearAllMedications() {
        val userId = _currentUserId.value ?: return
        viewModelScope.launch {
            medicamentoDao.deleteAllByUserId(userId)
        }
    }

}

data class LoginState(
    val isLoggedIn: Boolean = false,
    val userId: Int? = null,
    val loginError: String? = null
)

data class RegisterState(
    val isRegistered: Boolean = false,
    val registerError: String? = null
)
