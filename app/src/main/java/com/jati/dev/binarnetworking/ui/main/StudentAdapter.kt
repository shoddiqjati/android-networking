package com.jati.dev.binarnetworking.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jati.dev.binarnetworking.R
import com.jati.dev.binarnetworking.model.Student
import kotlinx.android.synthetic.main.item_student.view.*

/**
 * Created by Jati on 24/10/18.
 */

class StudentAdapter(private val students: List<Student>,
                     private val onItemEditListener: (Student, Int) -> Unit,
                     private val onItemDeleteListener: (Student, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.StudentHolder =
            StudentHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false))

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentAdapter.StudentHolder, position: Int) {
        holder.bindStudent(students[position], onItemEditListener, onItemDeleteListener)
    }

    inner class StudentHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindStudent(student: Student, editListener: (Student, Int) -> Unit,
                        deleteListener: (Student, Int) -> Unit) = with(itemView) {
            tvName.text = student.name
            tvEmail.text = student.email
            btnEdit.setOnClickListener { editListener(student, adapterPosition) }
            btnDelete.setOnClickListener { deleteListener(student, adapterPosition) }
        }
    }
}