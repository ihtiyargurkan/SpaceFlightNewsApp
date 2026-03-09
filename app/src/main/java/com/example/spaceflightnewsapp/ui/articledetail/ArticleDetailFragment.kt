package com.example.spaceflightnewsapp.ui.articledetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spaceflightnewsapp.databinding.FragmentArticleDetailBinding

/**
 * Displays full article details. Implementation in Adım 4.
 */
class ArticleDetailFragment : Fragment() {


    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleId = arguments?.getInt("article_id") ?: 0
        // Will be implemented in Adım 4
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
