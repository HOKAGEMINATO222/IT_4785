//package com.example.filemanager
//
//import Student
//import StudentDatabase
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat

//class EditStudentActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_edit_student)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        val name = findViewById<EditText>(R.id.edt_name)
//        val id = findViewById<EditText>(R.id.edt_id)
//
//        val nameText = intent.getStringExtra("name")
//        val idText = intent.getStringExtra("id")
//        val originalId = intent.getStringExtra("originalId")
//
//        name.setText(nameText)
//        id.setText(idText)
//
//        findViewById<Button>(R.id.btn_edt_std).setOnClickListener {
//            val newName = name.text.toString()
//            val newId = id.text.toString()
//
//            if (newName.isNotEmpty() && newId.isNotEmpty()) {
//                val resultIntent = intent
//                resultIntent.putExtra("name", newName)
//                resultIntent.putExtra("id", newId)
//                resultIntent.putExtra("originalId", originalId) // Include original ID
//                setResult(RESULT_OK, resultIntent)
//                finish()
//            } else {
//                if (newName.isEmpty()) name.error = "Name cannot be empty"
//                if (newId.isEmpty()) id.error = "ID cannot be empty"
//            }
//        }
//
//        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
//            setResult(RESULT_CANCELED)
//            finish()
//        }
//    }
//}

package com.example.filemanager

import Student
import StudentDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditStudentActivity : AppCompatActivity() {

    private lateinit var studentDatabase: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentDatabase = StudentDatabase.getInstance(this)

        val name = findViewById<EditText>(R.id.edt_name)
        val id = findViewById<EditText>(R.id.edt_id)

        val nameText = intent.getStringExtra("name")
        val studentIdText = intent.getStringExtra("studentId")
        val studentId = intent.getLongExtra("id", 0) // Get the student ID for update

        name.setText(nameText)
        id.setText(studentIdText)

        // Button to save the updated student
        findViewById<Button>(R.id.btn_edt_std).setOnClickListener {
            val newName = name.text.toString()
            val newStudentId = id.text.toString()

            if (newName.isNotEmpty() && newStudentId.isNotEmpty()) {
                // Update student in Room database
                val updatedStudent = Student(
                    id = studentId,  // Use the existing student ID for the update
                    studentName = newName,
                    studentId = newStudentId
                )

                // Launch a coroutine to update the student in Room database
                lifecycleScope.launch {
                    // Use withContext to perform the DB operation on the IO thread
                    withContext(Dispatchers.IO) {
                        val studentDao = studentDatabase.studentDao()

                        // Update student in the database
                        studentDao.updateStudent(updatedStudent)
                    }

                    // After the update, return result to calling activity
                    val resultIntent = intent
                    resultIntent.putExtra("name", newName)
                    resultIntent.putExtra("studentId", newStudentId)
                    resultIntent.putExtra("id", studentId) // Pass the updated student ID
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            } else {
                if (newName.isEmpty()) name.error = "Name cannot be empty"
                if (newStudentId.isEmpty()) id.error = "Student ID cannot be empty"
            }
        }

        // Button to cancel the edit and return
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}

