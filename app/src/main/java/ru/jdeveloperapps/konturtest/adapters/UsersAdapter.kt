package ru.jdeveloperapps.konturtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.google.common.base.CharMatcher
import kotlinx.android.synthetic.main.item_recycler.view.*
import ru.jdeveloperapps.konturtest.R
import ru.jdeveloperapps.konturtest.models.UserItem


class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ListViewHolder>(), Filterable {

    private var onItemClickListener: ((UserItem) -> Unit)? = null
    private var currentList = listOf<UserItem>()
    private var fullList = mutableListOf<UserItem>()

    fun setOnClickListener(listener: (UserItem) -> Unit) {
        onItemClickListener = listener
    }

    fun submitList(listUserItem: List<UserItem>) {
//        differ.submitList(listUserItem)
        currentList = listUserItem
        fullList.clear()
        fullList.addAll(listUserItem)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val curItem = currentList[position]
        holder.itemView.apply {
            name.text = curItem.name
            phone.text = curItem.phone
            heightText.text = curItem.height.toString()
            setOnClickListener {
                onItemClickListener?.let { it(curItem) }
            }
        }
    }

    override fun getItemCount(): Int =
        currentList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                var filteredList = mutableListOf<UserItem>()
                if (charSequence.isNullOrEmpty()) {
                    filteredList.addAll(fullList)
                } else {
                    val filterPattern = charSequence.toString().trim()
                    filteredList = fullList.filter { item ->
                        item.name.contains(filterPattern, true) ||
//                                item.phone.replace("""\D+""".toRegex(), "")
//                                    .contains(filterPattern, true)
                        CharMatcher.inRange('0','9').retainFrom(item.phone).contains(filterPattern)
                    }.toMutableList()
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterResults.values?.let {
                    currentList = filterResults.values as List<UserItem>
                }
                notifyDataSetChanged()
            }
        }
    }

}