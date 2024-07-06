package com.vsdhoni5034.mlmate.domain

import com.google.firebase.auth.FirebaseUser
import com.vsdhoni5034.mlmate.data.util.Resource

interface AuthenticationRepository {

    suspend fun loginUser(email: String, password: String): Resource<FirebaseUser?>

    suspend fun registerUser(email: String, password: String): Resource<FirebaseUser?>

    suspend fun logoutUser()

}