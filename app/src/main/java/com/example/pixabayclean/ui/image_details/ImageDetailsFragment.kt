package com.example.pixabayclean.ui.image_details

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pixabayclean.R
import com.example.pixabayclean.databinding.ImageDetailsFragmentBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailsFragment : Fragment() {

    private val viewModel: ImageDetailsViewModel by viewModels()
    private lateinit var binding: ImageDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.image_details_fragment, container, false
        )

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

        for (element in viewModel.allTags) {
            val chip = Chip(context)
            chip.apply {
                text = element
                textSize = 16F
                typeface = Typeface.create(ResourcesCompat.getFont(context, R.font.actor), Typeface.NORMAL)
                setChipBackgroundColorResource(R.color.blue_100)
            }
            binding.tagsChipGroup.addView(chip)
        }

        return binding.root
    }
}