package com.example.fininfocomtask.Realm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.fininfocomtask.MainActivity
import com.example.fininfocomtask.databinding.ActivityAddNoteBinding

class AddUserDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.saveButton.setOnClickListener {

            if (binding.nameEditText.text!!.isEmpty()) {
                binding.nameEditText.error = "Enter Name"
                binding.nameEditText.requestFocus()
                return@setOnClickListener
            }

            if (binding.ageEditText.text!!.isEmpty()) {
                binding.ageEditText.error = "Enter Age"
                binding.ageEditText.requestFocus()
                return@setOnClickListener
            }

            if (binding.cityEditText.text!!.isEmpty()) {
                binding.cityEditText.error = "Enter City"
                binding.cityEditText.requestFocus()
                return@setOnClickListener
            }

            if (binding.descriptionEditText.text!!.isEmpty()) {
                binding.descriptionEditText.error = "Enter description"
                binding.descriptionEditText.requestFocus()
                return@setOnClickListener
            }
                viewModel.addNote(
                    binding.nameEditText.text.toString(),
                    binding.descriptionEditText.text.toString(),
                    binding.ageEditText.text.toString(),
                    binding.cityEditText.text.toString(),
                )

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}