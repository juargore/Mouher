package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentProductReviewsBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel

class ProductReviewsFragment : Fragment() {

    private val viewModel: ProductReviewsViewModel by viewModel()
    private lateinit var binding: FragmentProductReviewsBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.backClicked -> activity?.onBackPressed()
            BR.showPopRating -> showPopUpRating()
            BR.error -> showErrorMsg()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductReviewsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        arguments?.let{
            val productId = it.getInt("productId")
            val storeId = it.getInt("storeId")

            viewModel.initialize(productId, storeId)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun showPopUpRating(){
        val d = Dialog(requireContext(), R.style.FullDialogTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.pop_add_rating)
            show()
        }

        val etName = d.findViewById<EditText>(R.id.etNameReview)
        val etEmail = d.findViewById<EditText>(R.id.etEmailReview)
        val etStars = d.findViewById<RatingBar>(R.id.ratingReview)
        val etComments = d.findViewById<EditText>(R.id.etCommentReview)
        val imgClose = d.findViewById<ImageView>(R.id.imgCloseReview)
        val txtSave = d.findViewById<TextView>(R.id.txtSaveReview)

        imgClose.setOnClickListener {
            d.dismiss()
        }

        txtSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val comments = etComments.text.toString().trim()
            val rate = etStars.rating

            if(name.isBlank() || email.isBlank() || comments.isBlank()){
                toast("Todos los datos son obligatorios.")
                return@setOnClickListener
            }

            if(rate == 0f){
                toast("La calificación con las estrellas es obligatorio.")
                return@setOnClickListener
            }

            if(email.isBlank()){
                toast("El correo electrónico es obligatorio.")
                return@setOnClickListener
            }

            // everythig goes well -> save on WS
            viewModel.onAddRatingFromPopupClicked(name, email, comments, rate)
            d.dismiss()
        }
    }

    private fun showErrorMsg(){
        with(viewModel.error){
            val errorType = if(hasErrors) SnackType.ERROR else SnackType.SUCCESS
            showSnackbar(binding.root, viewModel.error.message, errorType)
        }
    }

    fun itemViewBinder(): ItemBinder<AProductReviewViewModel> {
        return CompositeItemBinder(ProductsReviewsItemBinder(BR.viewModel, R.layout.recycler_item_review))
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}