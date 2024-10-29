package com.example.bai2

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bai2.adapter.StudentAdapter
import com.example.bai2.model.Student
import java.util.Locale.filter

class MainActivity3 : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private var students: List<Student> = listOf() // Danh sách sinh viên gốc
    private var filteredStudents: List<Student> = listOf() // Danh sách sinh viên sau khi lọc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        // Giả sử bạn đã có danh sách sinh viên
        students = listOf(
            Student("Nguyen Van A", "123456"),
            Student("Tran Thi B", "654321"),
            Student("Le Van C", "789012"),
            Student("Pham Thi D", "345678"),
            Student("Hoang Van E", "901234")
        )
        filteredStudents = students

        recyclerView = findViewById(R.id.recycler_view)
        adapter = StudentAdapter(filteredStudents)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText ?: "")
                return true
            }
        })
    }
    private fun filter(text: String) {
        filteredStudents = if (text.length > 2) {
            students.filter {
                it.name.contains(text, ignoreCase = true) || it.studentId.contains(text, ignoreCase = true)
            }
        } else {
            students
        }
        adapter.filterList(filteredStudents)
    }
}