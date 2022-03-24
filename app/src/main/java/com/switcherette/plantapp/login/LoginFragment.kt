package com.switcherette.plantapp.login

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentLoginBinding
import com.switcherette.plantapp.data.room.AppDB


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<ConstraintLayout>(R.id.cl_mainActivity).setBackgroundColor(
            ResourcesCompat.getColor(resources, R.color.primary_dark, null))
        binding = FragmentLoginBinding.bind(view)

        // AUTH

        // Initialize Firebase Auth
        auth = Firebase.auth
        Log.e("loginCurrentUser", "${auth.currentUser?.uid}")


        // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            if(currentUser != null){
                findNavController().navigate(R.id.action_loginFragment_to_homePlantFragment)

            } else { // If user is not loggedIn
                // This throws the launcher (UI)
                val signInLauncher = registerForActivityResult(
                    FirebaseAuthUIActivityResultContract()
                ) { res ->
                    this.onSignInResult(res)
                }

                // FirebaseUi flow
                // Choose authentication providers
                val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build())

                // Create and launch sign-in intent
                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.plapp_logo)
                    .setTheme(R.style.LoginTheme)
                    .build()
                signInLauncher.launch(signInIntent)
            }
    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Log.e("loginResponse", "$response")
        Log.e("loginResult", "${result.resultCode}")

        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            val userId = Firebase.auth.currentUser?.uid
            Log.e("loginUser", "$user")
            Log.e("loginUserId", "$userId")

            // CLOUD FIRESTORE: Create the user document in DB if it does not already exist.
            val db = Firebase.firestore
            val docRef = db.collection("Users").document("$userId")
            docRef.get()
                .addOnSuccessListener { document ->
                    Log.e("document", "$document")
                    if (document.exists()) {
                        Log.d("existing user", "DocumentSnapshot data: ${document.data}")
                    } else {
                        val userActive = hashMapOf("active" to true, "name" to user?.displayName, "email" to user?.email)
                        db.collection("Users").document("${user?.uid}")
                            .set(userActive, SetOptions.merge())
                            .addOnSuccessListener { Log.d("Success", "Document successfully written!") }
                            .addOnFailureListener { e -> Log.w("Failure", "Error writing document", e) }
                        Log.d("new user", "New User doc added")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("failure", "get failed with ", exception)
                }

            Toast.makeText(requireContext(), "You are in", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_homePlantFragment)
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
           //val error = response?.error?.errorCode
            val error = response?.error

            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()

            Log.e("loginError", "$error")
        }

    }





}