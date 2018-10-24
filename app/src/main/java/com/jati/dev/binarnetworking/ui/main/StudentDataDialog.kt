package com.jati.dev.binarnetworking.ui.main

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jati.dev.binarnetworking.R
import com.jati.dev.binarnetworking.model.Student
import kotlinx.android.synthetic.main.dialog_student_data.*

/**
 * Created by Jati on 24/10/18.
 */

class StudentDataDialog : DialogFragment() {

    lateinit var callback: StudentCallback
    var student: Student? = null
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_student_data, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        student?.let {
            etName.text = SpannableStringBuilder(it.name)
            etEmail.text = SpannableStringBuilder(it.email)
        }

        btnSave.setOnClickListener { _ ->
            if (student != null) {
                student?.let {
                    it.email = etEmail.text.toString()
                    it.name = etName.text.toString()
                    callback.editStudent(it, position)
                }
            }
            else {
                val newStudent = Student(name = etName.text.toString(), email = etEmail.text.toString())
                callback.addStudent(newStudent)
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        onDestroy()
    }

    interface StudentCallback {
        fun editStudent(student: Student, position: Int)
        fun addStudent(student: Student)
    }
}