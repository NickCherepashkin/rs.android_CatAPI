package com.drozdova.catapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.drozdova.catapi.adapter.CatAdapter
import com.drozdova.catapi.adapter.OnCatItemClickListener
import com.drozdova.catapi.data.Cat
import com.drozdova.catapi.databinding.FragmentCatsListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsListFragment : Fragment(R.layout.fragment_cats_list), OnCatItemClickListener {

    private val binding by viewBinding (FragmentCatsListBinding::bind)
    private val catViewModel: CatViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemAdapter = CatAdapter(requireContext(), this)
        itemAdapter.addLoadStateListener { loadState ->
            binding.rvCats.isVisible = loadState.refresh != LoadState.Loading
            binding.progress.isVisible = loadState.refresh == LoadState.Loading
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Toast.makeText(
                    context,
                    "${"Bad request"}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val lManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.rvCats.apply {
            adapter = itemAdapter
            layoutManager = lManager
        }

        viewLifecycleOwner.lifecycleScope.launch {
            catViewModel.catsFlow.collect {  pagingData ->
                itemAdapter.submitData(
                    viewLifecycleOwner.lifecycle,
                    pagingData
                )
            }
        }
    }

    override fun onItemClick(cat: Cat) {
        catViewModel.cat.value = cat
        commitTransaction(CatFragment())
    }

    private fun commitTransaction(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.flip_from_middle,
                R.anim.flip_to_middle,
            ).replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null).commit()
    }
}