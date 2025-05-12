package com.tipiz.newsappuninet.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tipiz.newsappuninet.databinding.FragmentDetailBinding
import com.tipiz.newsappuninet.uitls.Constant

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val url = arguments?.getString(Constant.EXTRA_DETAIL)
        url?.let {
            binding.webView.apply {
                loadUrl(it)
                webViewClient = object : WebViewClient(){
                    override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                        binding.pgbar.visibility = View.GONE;
                    }
                }
                settings.javaScriptEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.webView.destroy()
    }
}