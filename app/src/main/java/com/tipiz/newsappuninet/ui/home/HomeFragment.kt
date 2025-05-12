package com.tipiz.newsappuninet.ui.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.core.domain.model.CategoryModel
import com.tipiz.core.domain.model.DataHeadLinesNews
import com.tipiz.newsappuninet.R
import com.tipiz.newsappuninet.databinding.FragmentHomeBinding
import com.tipiz.newsappuninet.ui.adapter.CategoryAdapter
import com.tipiz.newsappuninet.ui.adapter.LoadStateAdapterNews
import com.tipiz.newsappuninet.ui.adapter.NewsAdapter
import com.tipiz.newsappuninet.ui.base.BaseFragment
import com.tipiz.newsappuninet.uitls.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel() //ktx
    private lateinit var adapter: NewsAdapter
    private lateinit var footerAdapter: LoadStateAdapterNews

    override fun initView() {

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    val searchView = it.actionView as SearchView
                    searchView.queryHint = getString(R.string.search)
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            query?.let {
                                viewModel.updateSearchQuery(it)
                            }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return true
                        }
                    })

                    searchView.setOnCloseListener {
                        viewModel.updateSearchQuery("")
                        true
                    }

                    true
                }

                else -> {
                    true
                }
            }
        }

        showCategory()
        initPagingGit()

        binding.rlStore.setOnRefreshListener {
            adapter.refresh()
            binding.rlStore.isRefreshing = false
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            news.observe(viewLifecycleOwner) { p ->
                showPagingGit(p)
            }

        }
    }

    private fun initPagingGit() {
        adapter = NewsAdapter(object : NewsAdapter.OnPagingListener {
            override fun onClick(news: DataHeadLinesNews) {
                val mBundle = Bundle()
                mBundle.putString(Constant.EXTRA_DETAIL, news.url)
                activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                    ?.findNavController()
                    ?.navigate(R.id.action_homeFragment_to_detailFragment, mBundle)
            }
        })

        footerAdapter = LoadStateAdapterNews()

        adapter.addLoadStateListener { loadState ->
            showLoading(loadState.refresh is LoadState.Loading)
        }
    }

    private fun showPagingGit(data: PagingData<DataHeadLinesNews>) {
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapter.withLoadStateFooter(
            footer = footerAdapter
        )
        adapter.submitData(lifecycle, data)
        adapter.notifyItemChanged(0)
    }

    private fun showCategory() {

        with(binding) {

            val adapter = CategoryAdapter(viewModel.categories, object : CategoryAdapter.OnAdapterListener {
                override fun onClick(categories: CategoryModel) {
                    viewModel.updateCategory(categories.id)
                }
            })

            rvCategory.adapter = adapter
            rvCategory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun showLoading(loadState: Boolean) {
        binding.apply {
            pgbar.visibility = if (loadState) View.VISIBLE else View.INVISIBLE
        }
    }
}
