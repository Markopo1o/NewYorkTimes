package hu.jm.newyorktimes.ui.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import hu.jm.newyorktimes.databinding.NewsRowBinding
import hu.jm.newyorktimes.model.News
import hu.jm.newyorktimes.ui.list.ListFragmentDirections

class NewsListAdapter: RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var oldData = emptyList<News>()

    inner class ViewHolder(binding: NewsRowBinding) : RecyclerView.ViewHolder(binding.root){
        var pic = binding.ivPic
        var title = binding.tvTitle
        var author = binding.tvAuthor
        var updated = binding.tvDate
        var arrow = binding.ivArrow
        var rowLayout = binding.rowLayout

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = oldData[position]
        holder.title.text = currentItem.title
        holder.author.text = currentItem.author
        holder.updated.text = currentItem.updated
        holder.pic.load(oldData[position].pic)

        holder.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return oldData.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<News>){
        this.oldData = newData
        notifyDataSetChanged()
    }
}