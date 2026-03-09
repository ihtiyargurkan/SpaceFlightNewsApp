package com.example.spaceflightnewsapp.ui.newslist

import android.os.Bundle
import com.example.spaceflightnewsapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        observeViewModel()
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

    private fun observeViewModel() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles ?: emptyList())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoading.visibility =
                if (isLoading == true) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = isLoading == true
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            binding.textError.visibility =
                if (message != null) View.VISIBLE else View.GONE
            binding.textError.text = message
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
