package com.example.stackoverflow.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackoverflow.R
import com.example.stackoverflow.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionAdapter = QuestionAdapter(viewModel)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this@MainFragment
        setupRecyclerView()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.questionFlow.collect {
                questionAdapter.questionInfos = it
                questionAdapter.notifyDataSetChanged()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            for (event in viewModel.eventChannel) {
                when (event) {
                    is MainViewModel.Event.OpenUrl -> {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(event.url)
                        startActivity(openURL)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.questionsView.layoutManager = LinearLayoutManager(requireContext())
        binding.questionsView.adapter = questionAdapter
    }

}