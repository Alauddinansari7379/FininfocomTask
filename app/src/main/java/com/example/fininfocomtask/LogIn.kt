package com.example.fininfocomtask

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fininfocomtask.databinding.ActivityLogInBinding
import com.example.fininfocomtask.utils.AppProgressBar
import com.example.fininfocomtask.utils.myToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class LogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private var auth = Firebase.auth
    private var email = ""
    private var password = ""
    private var passwordMatcher = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()




        binding.tvSignUp.setOnClickListener {
            binding.btnRegister.visibility = View.VISIBLE
            binding.btnLogIn.visibility = View.GONE
            binding.tvSignUp.visibility = View.GONE
            binding.tvLogin.visibility = View.VISIBLE
        }
        binding.tvLogin.setOnClickListener {
            binding.btnRegister.visibility = View.GONE
            binding.btnLogIn.visibility = View.VISIBLE
            binding.tvSignUp.visibility = View.VISIBLE
            binding.tvLogin.visibility = View.GONE
        }
        binding.btnLogIn.setOnClickListener {
            if (binding.edtUserName.text!!.isEmpty()) {
                binding.edtUserName.error = "Enter UserName"
                binding.edtUserName.requestFocus()
                return@setOnClickListener
            }
            if (binding.edtPassword.text!!.isEmpty()) {
                binding.edtPassword.error = "Enter Password"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }
            email = binding.edtUserName.text.toString().trim()
            password = binding.edtPassword.text.toString().trim()
            AppProgressBar.showLoaderDialog(this@LogIn)
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
                        Log.e("user", user.toString())
                        AppProgressBar.hideLoaderDialog()
                        myToast(this, "Log In Successfully")
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        finish()
                        startActivity(intent)
                    } else {
                        AppProgressBar.hideLoaderDialog()
                        myToast(this, "UserName Or Password Wrong")
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        //  Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT,).show()
                    }
                }
        }


        binding.btnRegister.setOnClickListener {
            if (binding.edtUserName.text!!.isEmpty()) {
                binding.edtUserName.error = "Enter UserName"
                binding.edtUserName.requestFocus()
                return@setOnClickListener
            }
            password = binding.edtPassword.text.toString().trim()
            if (binding.edtPassword.text!!.isEmpty()) {
                binding.edtPassword.error = "Enter Password"
                binding.edtPassword.requestFocus()
                return@setOnClickListener

            }
            if (binding.edtPassword.text!!.length < 7) {
                binding.edtPassword.error = "Password must be 7 Characters"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }

            if (!(password.contains("@") || password.contains("#")
                        || password.contains("!") || password.contains("~")
                        || password.contains("$") || password.contains("%")
                        || password.contains("^") || password.contains("&")
                        || password.contains("*") || password.contains("(")
                        || password.contains(")") || password.contains("-")
                        || password.contains("+") || password.contains("/")
                        || password.contains(":") || password.contains(".")
                        || password.contains(", ") || password.contains("<")
                        || password.contains(">") || password.contains("?")
                        || password.contains("|"))
            ) {

                binding.edtPassword.error = "Password must be SpecialCharacter and Numeric"

                if (!isValidPassword(password)){
                    binding.edtPassword.error = "Password must be with 1UpperCase Alphabet "
                }
                return@setOnClickListener
            }




            email = binding.edtUserName.text.toString().trim()
            AppProgressBar.showLoaderDialog(this@LogIn)

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.e("TAG", "createUserWithEmail:success")
                        val user = auth!!.currentUser
                        myToast(this@LogIn, "Successfully Registered")
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        finish()
                        startActivity(intent)
                        AppProgressBar.hideLoaderDialog()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        myToast(this@LogIn, "Registration failed")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

        }
    }

    private fun isValidPassword(password: String): Boolean {

        if (password.length < 7) {
            return false
        }
        var hasLower = false
        var hasUpper = false

        for (cha in password) {
            if (Character.isLowerCase(cha)) {
                hasLower = true
            } else if (Character.isUpperCase(cha)) {
                hasUpper = true
            }
        }
        return hasLower && hasUpper
    }
}
