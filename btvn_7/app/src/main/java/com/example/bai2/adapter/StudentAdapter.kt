package com.example.bai2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bai2.R
import com.example.bai2.model.Student

class StudentAdapter(private var students: List<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name_text_view)
        val idTextView: TextView = view.findViewById(R.id.id_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.nameTextView.text = student.name
        holder.idTextView.text = student.studentId
    }

    override fun getItemCount(): Int = students.size

    fun filterList(filteredStudents: List<Student>) {
        students = filteredStudents
        notifyDataSetChanged()
    }
}
