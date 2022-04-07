package by.godevelopment.sixthtask.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.sixthtask.R
import by.godevelopment.sixthtask.appComponent
import by.godevelopment.sixthtask.databinding.FragmentListBinding
import by.godevelopment.sixthtask.di.factory.ViewModelFactory
import by.godevelopment.sixthtask.domain.models.DialogEvent
import by.godevelopment.sixthtask.domain.models.SnackbarEvent
import by.godevelopment.sixthtask.presentation.adapter.MetaAdapter
import by.godevelopment.sixthtask.presentation.dialogfragments.ResultDialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

    private val setResultToHeader: (Int, String, String?) -> Unit = { id, key, resultOrNull ->
        viewModel.setResultToList(id, key, resultOrNull)
    }

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
        setupRecyclerView()
        setupEvent()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnSendResult.setOnClickListener {
            viewModel.sendFormToRemote()
        }
    }

    private fun setupUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                if (!uiState.isFetchingData) {
                    binding.progress.visibility = View.GONE
                } else binding.progress.visibility = View.VISIBLE
                setupToolbar(uiState.title)
                setupImage(uiState.imageLink)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MetaAdapter(setResultToHeader)
            lifecycleScope.launchWhenStarted {
                viewModel.listState.collect { list ->
                    (adapter as MetaAdapter).metaList = list
                }
            }
        }
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect {
                when(it) {
                    is SnackbarEvent -> Snackbar
                            .make(binding.root, it.message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.snackbar_btn_reload))
                            { viewModel.fetchMetaModel() }
                            .show()
                    is DialogEvent -> showDialog(it.message)
                }
            }
        }
    }

    private fun showDialog(message: String) {
        ResultDialogFragment.showDialog(parentFragmentManager, message)
    }

    private fun setupImage(src: String?) {
        src?.let {
            Glide.with(binding.root)
                .load(src)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.image_not_loaded)
                .placeholder(R.drawable.image)
                .into(binding.image)
        }
    }

    private fun setupToolbar(titleText: String) {
        binding.toolbar.apply {
            title = titleText
            subtitle = context.resources.getString(R.string.app_name)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}