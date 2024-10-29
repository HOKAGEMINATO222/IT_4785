package com.example.bai2


import androidx.appcompat.app.AppCompatActivity
import com.example.bai2.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShow.setOnClickListener {
            val inputText = binding.editText.text.toString()
            if (inputText.isBlank()) {
                displayError("Vui lòng nhập số nguyên dương.")
                return@setOnClickListener
            }

            val n = inputText.toIntOrNull()
            if (n == null || n < 0) {
                displayError("Số không hợp lệ. Vui lòng nhập số nguyên dương.")
                return@setOnClickListener
            }

            binding.textViewError.visibility = View.GONE // Xóa thông báo lỗi nếu có
            val listData = when {
                binding.radioEven.isChecked -> getEvenNumbers(n)
                binding.radioOdd.isChecked -> getOddNumbers(n)
                binding.radioSquare.isChecked -> getSquareNumbers(n)
                else -> {
                    displayError("Vui lòng chọn một loại số.")
                    return@setOnClickListener
                }
            }

            // Hiển thị danh sách trong RecyclerView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listData)
            binding.rcv.adapter = adapter
        }
    }

    private fun displayError(message: String) {
        binding.textViewError.text = message
        binding.textViewError.visibility = View.VISIBLE
    }

    private fun getEvenNumbers(n: Int): List<Int> {
        return (0..n).filter { it % 2 == 0 }
    }

    private fun getOddNumbers(n: Int): List<Int> {
        return (1..n).filter { it % 2 != 0 }
    }

    private fun getSquareNumbers(n: Int): List<Int> {
        return (0..n).filter { isPerfectSquare(it) }
    }

    private fun isPerfectSquare(number: Int): Boolean {
        val root = sqrt(number.toDouble()).toInt()
        return root * root == number
    }
}
