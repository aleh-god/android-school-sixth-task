package by.godevelopment.sixthtask.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.godevelopment.sixthtask.R
import by.godevelopment.sixthtask.appComponent
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.databinding.FragmentListBinding
import by.godevelopment.sixthtask.di.factory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]
        setupUi()
        setupEvent()
        return binding.root
    }

    private fun setupUi() {
        lifecycleScope.launchWhenStarted {
            Log.i(TAG, "setupUi: lifecycleScope.launchWhenStarted")
            viewModel.uiState.collect { uiState ->
                Log.i(TAG, "setupUi: launchWhenStarted Data = ${uiState.dataList}")
                if (!uiState.isFetchingData) {
                    Log.i(TAG, "setupUi: uiState.isFetchingData = false")
                    binding.progress.visibility = View.GONE
                } else binding.progress.visibility = View.VISIBLE

                binding.message.text = uiState.dataList

//                val adapter = MetaAdapter().apply {
//                    imagesList = uiState.imagesList
//                }
//                binding.rv.adapter = adapter
//                val layoutManager =
//                    if (isTablet()) GridLayoutManager(requireContext(), 3)
//                    else GridLayoutManager(requireContext(), 2)
//                binding.rv.layoutManager = layoutManager

            }
        }
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect {
                Log.i(TAG, "setupEvent: viewModel.uiEvent.collect")
                Snackbar.make(binding.root,
                    R.string.alert_error_loading,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.snackbar_btn_reload))
                    { viewModel.fetchImagesList() }
                    .show()
            }
        }
    }
}