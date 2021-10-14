package com.drozdova.catapi

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.drozdova.catapi.databinding.FragmentCatBinding

class CatFragment : Fragment(R.layout.fragment_cat) {
    private val binding by viewBinding(FragmentCatBinding::bind)
    private val catViewModel: CatViewModel by activityViewModels()


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cat = catViewModel.cat

        if (cat != null) {
            binding.imageViewFull.load(cat.url)
	    binding.textViewWidth.append(cat.width)
            binding.textViewHeight.append(cat.height)
            binding.textViewName.append(cat.id)
        }

        binding.saveButton.setOnClickListener {
            val imageId: String = cat?.id ?: ""
            if (binding.imageViewFull.drawable != null) (
                    saveImage(binding.imageViewFull.drawable, imageId)?.let {
                        showToast(
                            requireContext(),
                            "${"Saved"} $imageId"
                        )
                    }) else {
                showToast(requireContext(), "Empty image")
            }
        }
    }

    private fun saveImage(drawable: Drawable, title: String): String? {
        val bitmap = (drawable as BitmapDrawable).bitmap
        return MediaStore.Images.Media.insertImage(
            context?.contentResolver,
            bitmap,
            title,
            "${"jjjjj"} $title"
        )
    }

    private fun showToast(context: Context, msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}