package com.vsdhoni5034.mlmate.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vsdhoni5034.mlmate.data.util.Resource
import com.vsdhoni5034.mlmate.domain.AuthenticationRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val auth = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(auth.user)
        } catch (ex: Exception) {
            Resource.Error(ex.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun registerUser(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val auth = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(auth.user)
        } catch (ex: Exception) {
            Resource.Error(ex.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
    }
}