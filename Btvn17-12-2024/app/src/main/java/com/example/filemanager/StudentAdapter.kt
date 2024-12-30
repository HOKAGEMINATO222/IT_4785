package com.example.filemanager

import Student
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class StudentAdapter(
    private val students: MutableList<Student>, // Use the Room entity 'Student'
    private val context: Context
) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Student = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_student, parent, false)

        val student: Student = getItem(position)

        val nameTextView = view.findViewById<TextView>(R.id.tv_student_name)
        val idTextView = view.findViewById<TextView>(R.id.tv_student_id)

        nameTextView.text = student.studentName
        idTextView.text = student.studentId

        return view
    }
}
