package com.switcherette.plantapp.profile.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentMyProfileBinding
import com.switcherette.plantapp.profile.viewModel.MyProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private lateinit var binding: FragmentMyProfileBinding
    private lateinit var user: FirebaseUser
    private val viewModel: MyProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyProfileBinding.bind(view)
        user = (Firebase.auth).currentUser!!

        with(binding) {
            tvName.text = user.displayName
            tvMail.text = user.email
            tvPassword.setOnClickListener { showChangePasswordDialog() }
            tvSignOut.setOnClickListener { signUserOut() }
            tvDelete.setOnClickListener { deleteUser() }
            scToggleNotifications.isChecked = viewModel.showNotifications
            scToggleNotifications.setOnCheckedChangeListener { _, showNotification ->
                viewModel.updateShowNotifications(showNotification)
            }
            tvRestoreBackup.setOnClickListener { viewModel.restoreBackup() }
        }
    }

    private fun signUserOut() {
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginFragment)
            }
    }

    private fun deleteUser() {
        AuthUI.getInstance()
            .delete(requireContext())
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "User deleted", Toast.LENGTH_SHORT).show()
                AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener {
                        findNavController().navigate(R.id.loginFragment)
                    }
            }
    }

    private fun updatePassword(newPassword: String) {
        user.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("PW", "User password updated.")
                }
            }
        Toast.makeText(requireContext(), "Password changed!", Toast.LENGTH_SHORT).show()
    }

    private fun showChangePasswordDialog() {
        var newPassword = ""

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Input your new password")

        val input = TextInputEditText(requireContext())

        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton(
            "SAVE NEW PASSWORD"
        ) { dialog, which ->
            newPassword = input.text.toString()
            updatePassword(newPassword)
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

}