package com.example.weatherapp.ui.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSecondBinding
import com.example.weatherapp.ui.adapter.WeatherListAdapter
import com.example.weatherapp.ui.extensions.toPercentageString
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.itemsList.layoutManager = llm
        binding.itemsList.adapter = WeatherListAdapter(ArrayList())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRestart.setOnClickListener {
            val viewModel: SecondFragmentViewModel by viewModels()
            viewModel.resetProgress()
            setVisibility(false)
        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        val viewModel: SecondFragmentViewModel by viewModels()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    binding.progressBar.progress = it.progressValue
                    binding.textviewSecond.text = getString(it.waitingText)
                    binding.progressValue.text = it.progressValue.toPercentageString()
                    if (it.item != null)
                        (binding.itemsList.adapter as WeatherListAdapter).add(it.item)
                    if (binding.progressBar.progress < 100)
                        setVisibility(false)
                    else {
                        binding.textviewSecond.text = getString(R.string.loading_done)
                        setVisibility(true)
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setVisibility(visibility: Boolean) {
        if (visibility) {
            binding.progressBar.visibility = View.GONE
            binding.itemsList.visibility = View.VISIBLE
            binding.buttonRestart.visibility = View.VISIBLE
            binding.progressValue.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.VISIBLE
            binding.itemsList.visibility = View.GONE
            binding.buttonRestart.visibility = View.GONE
            binding.progressValue.visibility = View.VISIBLE
        }
    }
}