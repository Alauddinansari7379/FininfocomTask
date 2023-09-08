package com.example.fininfocomtask

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.fininfocomtask.Realm.AddUserDataActivity
import com.example.fininfocomtask.Realm.MainViewModel
import com.example.fininfocomtask.Realm.UserAdapter
import com.example.fininfocomtask.databinding.ActivityMainBinding
import com.example.fininfocomtask.utils.myToast
import com.kanyideveloper.realmdatabasedemo.UserData
import java.util.*

class MainActivity : AppCompatActivity(),UserAdapter.SortBy {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: UserAdapter
    private lateinit var viewModel: MainViewModel
    private var id: String? = ""
    var dialog: Dialog? = null
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this@MainActivity)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        notesAdapter = UserAdapter(UserAdapter.OnClickListener { note ->
            createUpdateDialog(note)
        },this@MainActivity, UserAdapter.OnSwiper {
            id = it.id
        })
        binding.image.setOnClickListener {
            viewModel.deleteAllUser()
            notesAdapter.notifyDataSetChanged()
            refresh()
        }

        binding.imageLogOut.setOnClickListener {
            sessionManager.isLogin = false
            viewModel.deleteAllUser()
            val intent = Intent(applicationContext, LogIn::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }

        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNote(id!!)
                myToast(this@MainActivity, "User Deleted Successfully")

            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.userRecyclerview)

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddUserDataActivity::class.java))
        }

        viewModel.allNotes.observe(this) { allNotes ->
            notesAdapter.submitList(allNotes)
            binding.userRecyclerview.adapter = notesAdapter
        }

    }

    @SuppressLint("MissingInflatedId")
    private fun createUpdateDialog(userData: UserData) {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.update_dialog, viewGroup, false)
        val builder = AlertDialog.Builder(this)

        val titleEdtxt: EditText = dialogView.findViewById(R.id.nameEditTextUpdate)
        val age: EditText = dialogView.findViewById(R.id.ageEditTextUpdate)
        val city: EditText = dialogView.findViewById(R.id.cityEditTextUpdate)
        val descriptionEdtxt: EditText = dialogView.findViewById(R.id.descriptionEditTextUpdate)

        titleEdtxt.setText(userData.name)
        age.setText(userData.age)
        city.setText(userData.city)
        descriptionEdtxt.setText(userData.description)

        builder.setView(dialogView)
        builder.setTitle("Update User")
        builder.setPositiveButton("Update") { _, _ ->
            viewModel.updateNote(
                userData.id,
                titleEdtxt.text.toString(),
                age.text.toString(),
                city.text.toString(),
                descriptionEdtxt.text.toString()
            )
            notesAdapter.notifyDataSetChanged()
        }

        builder.setNegativeButton("Cancel") { _, _ ->
            myToast(this@MainActivity, "Canceled Update")
        }

        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.delete_all, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) {
            viewModel.deleteAllUser()
            notesAdapter.notifyDataSetChanged()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() {
        overridePendingTransition(0, 0)
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
    var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
             finishAffinity()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun sortBy() {
        var view = layoutInflater.inflate(R.layout.sortby_dialog, null)
        dialog = Dialog(this@MainActivity)
         val btnOkDialog = view!!.findViewById<Button>(R.id.btnOkDialogNew)
        val name = view!!.findViewById<TextView>(R.id.tvSortByName)
        val age = view!!.findViewById<TextView>(R.id.tvSortByAge)
        val city = view!!.findViewById<TextView>(R.id.tvSortByCity)
        dialog = Dialog(this@MainActivity)

        name.setOnClickListener {
            dialog?.dismiss()

        }

        age.setOnClickListener {
            dialog?.dismiss()

        }
        city.setOnClickListener {
            dialog?.dismiss()

        }




        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view) // <- fix
        }
        dialog!!.setContentView(view)
        dialog?.setCancelable(true)
        // dialog?.setContentView(view)
        // val d1 = format.parse("2023/03/29 11:04:00")
//        Log.e("currentDate", currentTime)
//        Log.e("EndTime", startTime)


        dialog?.show()
        btnOkDialog.setOnClickListener {
            dialog?.dismiss()
        }

     }
}