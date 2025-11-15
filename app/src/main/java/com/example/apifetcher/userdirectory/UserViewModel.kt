package com.example.apifetcher.userdirectory

import androidx.lifecycle.ViewModel // <-- CHANGED: No longer an AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
// No Dispatchers.IO needed, Retrofit handles its own threads
// No ExperimentalCoroutinesApi needed

class UserViewModel : ViewModel() { // <-- CHANGED: Inherits from ViewModel

    // Get instance of API service
    // private val userDao = UserDatabase.getDatabase(application).userDao() // <-- REMOVED
    private val apiService = UserRetrofitInstance.api

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // This new flow will hold the single list of users from the API
    // This is just like _notes in your NotesViewModel
    private val _apiUsers = MutableStateFlow<List<User>>(emptyList())

    /**
     * This is the "Single Source of Truth" for the UI.
     * It uses `combine` to react to changes in the user list OR the search query.
     * It filters the in-memory list.
     */
    val users: StateFlow<List<User>> =
        combine(_apiUsers, _searchQuery) { users, query ->
            if (query.isBlank()) {
                users // Return full list if search is empty
            } else {
                // Filter the in-memory list
                users.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.email.contains(query, ignoreCase = true)
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList() // Start with an empty list
            )

    init {
        fetchUsersFromApi()
    }

    /**
     * Fetches fresh users from the API and inserts them into our in-memory list.
     */
    fun fetchUsersFromApi() {
        // No Dispatchers.IO needed
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Step 1: Try to fetch fresh data
                val usersFromApi = apiService.getUsers()

                // Step 2: If successful, update in-memory list
                _apiUsers.value = usersFromApi
                // userDao.insertAll(usersFromApi) // <-- REMOVED

            } catch (e: Exception) {
                // Step 3: If API fails, log it and set an empty list
                println("Error fetching users: ${e.message}")
                _apiUsers.value = emptyList() // Clear list on error
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Updates the search query. The 'users' StateFlow will
     * automatically react and emit a new filtered list.
     */
    fun searchUsers(query: String) {
        _searchQuery.value = query
    }
}