package com.jati.dev.binarnetworking.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.jati.dev.binarnetworking.R
import com.jati.dev.binarnetworking.model.Student
import com.jati.dev.binarnetworking.network.ApiManager
import com.jati.dev.binarnetworking.utils.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val studentList = mutableListOf<Student>()
    private val studentAdapter by lazy {
        StudentAdapter(studentList, { student, position ->
            studentDataDialog.position = position
            studentDataDialog.student = student
            studentDataDialog.show(supportFragmentManager, studentDataDialog.tag)
        })
        { student, position -> deleteStudent(student, position) }
    }

    private val apiManager = ApiManager()

    private val deleteDialog by lazy {
        AlertDialog.Builder(this)
    }

    private val studentDataDialog by lazy {
        StudentDataDialog().apply {
            callback = object : StudentDataDialog.StudentCallback {
                override fun editStudent(student: Student, position: Int) {
                    editStudentData(student, position)
                }

                override fun addStudent(student: Student) {
                    newStudent(student)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(rvStudent) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = studentAdapter
        }

        loadStudents()

        fab.setOnClickListener {
            studentDataDialog.position = 0
            studentDataDialog.student = null
            studentDataDialog.show(supportFragmentManager, studentDataDialog.tag)
        }
    }

    private fun deleteStudent(student: Student, position: Int) {
        deleteDialog.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }.setPositiveButton(getString(R.string.yes)) { _, _ ->
            student.id?.let { id ->
                apiManager.deleteStudent(id, {
                    toast(it)

                }) { toast(it) }
            }
        }.setTitle(R.string.delete_student)
            .setMessage("${getString(R.string.are_you_sure_delete)} ${student.name}?").create()

        deleteDialog.show()
    }

    private fun editStudentData(student: Student, position: Int) {
        apiManager.editStudent(student, {
            studentDataDialog.dismiss()
            studentList[position] = it
            studentAdapter.notifyItemChanged(position)
        }) { toast(it) }
    }

    private fun loadStudents() {
        apiManager.loadAllStudent({
            studentList.clear()
            studentList.addAll(it)
            studentAdapter.notifyDataSetChanged()
        }) { toast(it) }
    }

    private fun newStudent(student: Student) {
        apiManager.addStudent(student, {
            studentDataDialog.dismiss()
            studentList.add(it)
            studentAdapter.notifyItemInserted(studentList.size - 1)
        }) { toast(it) }
    }
}
