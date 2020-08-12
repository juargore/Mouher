package com.glass.data.repositories.firebaseauth

import com.glass.domain.repositories.IUserRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth

class UserRepository(private val firebaseAuth: FirebaseAuth): IUserRepository {

    override fun login(user: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    it.result?.user?.let { authUser ->

                        return@addOnCompleteListener
                    }
                } else {
                    if(it.exception is FirebaseNetworkException){

                    }
                }
            }
    }

    override fun logout() {

    }

}