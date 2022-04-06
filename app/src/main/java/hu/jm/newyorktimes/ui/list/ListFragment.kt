package hu.jm.newyorktimes.ui.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import hu.jm.newyorktimes.R
import hu.jm.newyorktimes.databinding.FragmentListBinding
import hu.jm.newyorktimes.model.News
import hu.jm.newyorktimes.ui.list.adapter.NewsListAdapter
import kotlinx.coroutines.launch

class ListFragment : Fragment(), SearchView.OnQueryTextListener{

    private val viewModel: ListViewModel by viewModels()
    private lateinit var pic: Bitmap
    private val adapter = NewsListAdapter()
    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        viewModel.getLiveData().observe(this
        ) { result -> render(result) }
        val recyclerView = _binding!!.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.readAllDataInViewModel.observe(viewLifecycleOwner) { news ->
            adapter.setData(news)
        }
        binding.swiperefresh.setOnRefreshListener {
            viewModel.getNewsInViewModel()
        }
    }
    private suspend fun getBitmap(url: String): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(url)
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
    private fun render(result: ListViewState) {
        when (result) {
            is InProgress -> {
                binding.swiperefresh.isEnabled = false
            }
            is ResponseSuccess -> {
                var i = 0
                lifecycleScope.launch {
                    viewModel.deleteAllUsers()
                    while (i < result.data.results.size){
                        pic = if(result.data.results[i].media.isEmpty()){
                            getBitmap("https://developer.nytimes.com/logos/logo?v=1600101622804")
                        }else{
                            getBitmap(result.data.results[i].media[0].mediaMetadata!![0]!!.url)
                        }
                        val news = News(
                            pic = pic,
                            title = result.data.results[i].title,
                            author = result.data.results[i].byline,
                            updated = result.data.results[i].updated,
                            url = result.data.results[i].url
                        )
                        viewModel.addNewsInViewModel(news)
                        i++
                    }
                }
                binding.swiperefresh.isRefreshing = false
                binding.swiperefresh.isEnabled = true
            }
            is ResponseError -> {
                Toast.makeText(requireContext(), result.exceptionMsg, Toast.LENGTH_SHORT).show()
                binding.swiperefresh.isRefreshing = false
                binding.swiperefresh.isEnabled = true
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.search_menu, menu)
        inflater.inflate(R.menu.more_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }
    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }
    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.setData(it)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}