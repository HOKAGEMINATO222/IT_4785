package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentListView: ListView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<StudentModel>() // Danh sách sinh viên

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        studentListView = binding.studentListView

        val studentNames = listOf(
            "Nguyen Van A", "Tran Thi B", "Le Van C", "Pham Thi D", "Hoang Van E",
            "Dang Thi F", "Vo Van G", "Bui Thi H", "Do Van I", "Nguyen Thi J",
            "Tran Van K", "Le Thi L", "Pham Van M", "Hoang Thi N", "Dang Van O",
            "Vo Thi P", "Bui Van Q", "Do Thi R", "Nguyen Van S", "Tran Thi T"
        )
        for (i in studentNames.indices) {
            val student = StudentModel(
                studentName = studentNames[i],
                studentId = "20230${i + 1}"
            )
            students.add(student)
        }

        adapter = StudentAdapter(this, students)
        studentListView.adapter = adapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(studentListView)


        studentListView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Selected: ${students[position].studentName}", Toast.LENGTH_SHORT).show()
        }
    }

    // Tạo menu Option
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuAdd) {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivityForResult(intent, 1) // Chờ kết quả từ AddStudentActivity
        }
        return super.onOptionsItemSelected(item)
    }

    // Tạo Context Menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        when (item.itemId) {
            R.id.menuEdit -> {
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("student", students[position])
                intent.putExtra("position", position)
                startActivityForResult(intent, 2)
            }
            R.id.menuRemove -> {
                students.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }
        return super.onContextItemSelected(item)
    }

    // Xử lý kết quả trả về từ các Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> { // Thêm mới sinh viên
                    val newStudent = data?.getSerializableExtra("student") as StudentModel
                    students.add(newStudent)
                    adapter.notifyDataSetChanged()
                }
                2 -> { // Cập nhật thông tin sinh viên
                    val position = data?.getIntExtra("position", -1) ?: -1
                    val updatedStudent = data?.getSerializableExtra("student") as StudentModel
                    if (position != -1) {
                        students[position] = updatedStudent
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
