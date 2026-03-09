package com.example.spaceflightnewsapp.ui.newslist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceflightnewsapp.R
import com.example.spaceflightnewsapp.databinding.FragmentNewsListBinding

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsListViewModel by viewModels()

    private lateinit var adapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()
        setupToolbar()
        setupRetryButton()
        observeViewModel()
    }

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_favorites -> {
                    findNavController().navigate(R.id.favoritesFragment)
                    true
                }
                else -> false
            }
        }
        setupSearchView()
    }

    private fun setupSearchView() {
        val searchItem = binding.toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView ?: return

        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                viewModel.search(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    viewModel.search(searchView.query?.toString())
                }
                searchHandler.postDelayed(searchRunnable!!, 500)
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = NewsListAdapter { article ->
            val bundle = Bundle().apply { putInt("article_id", article.id) }
            findNavController().navigate(R.id.articleDetailFragment, bundle)
        }
        binding.recyclerNews.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNews.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupRetryButton() {
        binding.buttonRetry.setOnClickListener {
            viewModel.refresh()
        }
    }

    private fun observeViewModel() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            val list = articles ?: emptyList()
            adapter.submitList(list)
            updateEmptyState(list)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoading.visibility =
                if (isLoading == true) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = isLoading == true
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            val hasError = message != null
            binding.layoutError.visibility = if (hasError) View.VISIBLE else View.GONE
            binding.textError.text = message
            if (hasError) {
                binding.layoutEmpty.visibility = View.GONE
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateEmptyState(articles: List<com.example.spaceflightnewsapp.data.model.Article>) {
        val hasError = viewModel.errorMessage.value != null
        val isLoading = viewModel.isLoading.value == true
        if (!hasError && !isLoading && articles.isEmpty()) {
            binding.layoutEmpty.visibility = View.VISIBLE
            binding.textEmpty.text = getString(R.string.no_search_results)
        } else {
            binding.layoutEmpty.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
