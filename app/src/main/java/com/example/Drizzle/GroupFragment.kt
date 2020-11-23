package com.example.Drizzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.fragment_group.*

class GroupFragment : Fragment() {
    /*var recyclerView: RecyclerView? = null
    var s1: Array<String>
    var s2: Array<String>*/

    private lateinit var groupListenerRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true

    private lateinit var peopleSection: Section

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       /* val root = inflater.inflate(R.layout.fragment_group, container, false)
        recyclerView = root.findViewById(R.id.recycleViewGroup)
        s1 = resources.getStringArray(R.array.group_titles)
        s2 = resources.getStringArray(R.array.group_desc)
        val myAdapter = MyAdapter(context, s1, s2)
        recyclerView.setAdapter(myAdapter)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        return root*/
        groupListenerRegistration = FirestoreUtil.addGroupsListener(this.requireActivity())

        return inflater.inflate(R.layout.fragment_group, container, false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removeListener(groupListenerRegistration)
        shouldInitRecyclerView = true
    }

    private fun updateRecyclerView(items: List<Item>) {
        fun init() {
            recycleViewGroup.apply{
                layoutManager = LinearLayoutManager(this@GroupFragment.context)
                adapter = GroupAdapter<RecyclerView.ViewHolder>().apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() {

        }

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()
    }
}