package com.example.Drizzle.recyclerview.item

import android.content.Context
import com.example.Drizzle.Group
import com.example.Drizzle.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_person.*

class PersonItem(val group: Group, val userId: String, private val context: Context)
    : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textView_name.text = group.groupName
        viewHolder.textView_bio.text = group.studyTopic
    }

    override fun getLayout() = R.layout.item_person
}