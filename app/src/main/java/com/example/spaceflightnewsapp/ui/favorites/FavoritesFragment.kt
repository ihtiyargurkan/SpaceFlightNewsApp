package com.example.spaceflightnewsapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceflightnewsapp.R
import com.example.spaceflightnewsapp.databinding.FragmentFavoritesBinding
import com.example.spaceflightnewsapp.ui.newslist.NewsListAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var adapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupToolbar()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = NewsListAdapter { article ->
            val bundle = Bundle().apply { putInt("article_id", article.id) }
            findNavController().navigate(R.id.articleDetailFragment, bundle)
        }
        binding.recyclerFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavorites.adapter = adapter
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.favorites.observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles ?: emptyList())
            binding.textEmpty.visibility =
                if (articles.isNullOrEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
