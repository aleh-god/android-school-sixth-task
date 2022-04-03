package by.godevelopment.sixthtask.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.sixthtask.R
import by.godevelopment.sixthtask.appComponent
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.databinding.FragmentListBinding
import by.godevelopment.sixthtask.di.factory.ViewModelFactory
import by.godevelopment.sixthtask.presentation.adapter.MetaAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        Toast.makeText(requireContext(), "ResultToList: $key to $resultOrNull" , Toast.LENGTH_SHORT)
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
                setupToolbar(uiState.title)
                setupImage(uiState.imageLink)
                binding.recyclerView.apply {
                    adapter = MetaAdapter(uiState.dataList, setResultToHeader)
                    layoutManager = LinearLayoutManager(requireContext())
                }
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
                    { viewModel.fetchMetaModel() }
                    .show()
            }
        }
    }

    private fun setupImage(src: String) {
//        if (src != null) {
            Glide.with(binding.root)
                .load(src)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.image_not_loaded)
                .placeholder(R.drawable.image)
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        showErrorResponse()
//                        return false
//                    }
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        _binding?.let {
//                            it.progress.visibility = View.GONE
//                        }
//                        return false
//                    }
//                })
                .into(binding.image)
//        } else {
//            showErrorResponse()
//        }
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