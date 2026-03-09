package com.example.spaceflightnewsapp.ui.articledetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.spaceflightnewsapp.R
import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.databinding.FragmentArticleDetailBinding
import com.example.spaceflightnewsapp.util.DateUtils

/**
 * Displays full article details including title, summary, image, and publication date.
 */
class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleDetailViewModel by viewModels()

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
        viewModel.loadArticle(articleId)

        setupToolbar()
        setupReadMoreButton()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupReadMoreButton() {
        binding.buttonReadMore.setOnClickListener {
            viewModel.article.value?.url?.let { url ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Cannot open link", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.article.observe(viewLifecycleOwner) { article ->
            article?.let { displayArticle(it) }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoading.visibility =
                if (isLoading == true) View.VISIBLE else View.GONE
            binding.buttonReadMore.visibility =
                if (isLoading == true) View.GONE else View.VISIBLE
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

    private fun displayArticle(article: Article) {
        binding.toolbar.title = article.newsSite
        binding.textTitle.text = article.title
        binding.textSummary.text = article.summary.trim()
        binding.textPublishedDate.text =
            getString(R.string.published_on, DateUtils.formatPublishedDate(article.publishedAt))
        binding.textNewsSite.text = getString(R.string.source, article.newsSite)

        article.imageUrl?.let { url ->
            binding.imageArticle.load(url) {
                placeholder(R.drawable.ic_launcher_foreground)
                error(R.drawable.ic_launcher_foreground)
            }
        } ?: run {
            binding.imageArticle.setImageResource(R.drawable.ic_launcher_foreground)
        }

        binding.textTitle.visibility = View.VISIBLE
        binding.textSummary.visibility = View.VISIBLE
        binding.textPublishedDate.visibility = View.VISIBLE
        binding.textNewsSite.visibility = View.VISIBLE
        binding.imageArticle.visibility = View.VISIBLE
        binding.buttonReadMore.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
