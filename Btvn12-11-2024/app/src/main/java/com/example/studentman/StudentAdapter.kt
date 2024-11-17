package com.example.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.studentman.databinding.LayoutStudentItemBinding

class StudentAdapter(
    private var students: List<StudentModel>,
    private val onEditClick: (StudentModel) -> Unit,
    private val onRemoveClick: (StudentModel) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(private val binding: LayoutStudentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: StudentModel) {
            binding.textStudentName.text = student.studentName
            binding.textStudentId.text = student.studentId

            binding.imageEdit.setOnClickListener {
                onEditClick(student)
            }

            binding.imageRemove.setOnClickListener {
                showDeleteConfirmationDialog(student)
            }
        }

        // Hộp thoại xác nhận xóa sinh viên
        private fun showDeleteConfirmationDialog(student: StudentModel) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.studentName}?")
                .setPositiveButton("Xóa") { _, _ ->
                    // Khi người dùng xác nhận xóa
                    onRemoveClick(student)
                }
                .setNegativeButton("Hủy", null)
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = LayoutStudentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    fun updateData(newStudents: List<StudentModel>) {
        students = newStudents
        notifyDataSetChanged()
    }
}
