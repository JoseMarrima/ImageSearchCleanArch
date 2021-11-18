package com.example.pixabayclean.ui.search_images

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.pixabayclean.R
import com.example.pixabayclean.databinding.SearchImageFragmentBinding
import com.example.pixabayclean.domain.model.Image
import com.example.pixabayclean.utils.Resource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchImageFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var binding: SearchImageFragmentBinding
    private var currentWordToQuery: String? = null
    private val disposable = CompositeDisposable()
    private val viewModel: SearchImageViewModel by viewModels()
    private val adapter = SearchImageAdapter(SearchImageClickListener {
        showDialog(image = it)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.search_image_fragment, container, false
        )

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            searchImageRv.adapter = adapter
        }
        setHasOptionsMenu(true)
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        viewModel.response.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    displayProgressBar()
                    Timber.d("Loading")
                }
                Resource.Status.SUCCESS -> it.data?.let { images ->
                    displayImages(images)
                }
                Resource.Status.ERROR -> {
                    Timber.d("error")
                    displayError(it.message)
                }
            }
        })
    }

    private fun displayImages(images: PagingData<Image>) {
        displayAndHide(recyclerView = View.VISIBLE, progressBar = View.GONE, error = View.GONE)
        lifecycleScope.launch {  adapter.submitData(images) }
    }

    private fun displayError(error: String?) {
        displayAndHide(recyclerView = View.GONE, progressBar = View.GONE, error = View.VISIBLE)
        Snackbar.make(
            binding.root,
            "Sorry, $error",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun displayProgressBar() {
        displayAndHide(recyclerView = View.GONE, progressBar = View.VISIBLE, error = View.GONE)
    }

    private fun displayAndHide(recyclerView: Int, progressBar: Int, error: Int) {
        binding.searchImageRv.visibility = recyclerView
        binding.progressBar.visibility = progressBar
        binding.errorImageView.visibility = error
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.d("*** search clicked ***********")
                query?.let { searchQuery ->
                    if (query == currentWordToQuery) return true
                    currentWordToQuery = query
                    viewModel.searchImages(searchQuery)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.menu.search_menu -> {
                Timber.d("*** clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog(image: Image) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Do you want to see more details?")
            .setPositiveButton("Yes") { _, _ ->
                findNavController().navigate(
                    SearchImageFragmentDirections.actionSearchImageFragmentToImageDetailsFragment(
                        image
                    )
                )
            }
            .setNegativeButton("No", null)
            .show()
    }
}