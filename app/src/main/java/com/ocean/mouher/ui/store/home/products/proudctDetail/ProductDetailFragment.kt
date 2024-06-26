package com.ocean.mouher.ui.store.home.products.proudctDetail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.mouher.R
import com.ocean.mouher.databinding.FragmentProductDetailBinding
import com.ocean.mouher.extensions.isEmailValid
import com.ocean.mouher.extensions.openOrRefreshFragment
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.binder.CompositeItemBinder
import com.ocean.mouher.ui.common.binder.ItemBinder
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.common.showSnackbar
import com.ocean.mouher.ui.store.home.HomeStoreNewProductsAdapter
import com.ocean.mouher.ui.store.home.products.proudctDetail.reviews.ProductReviewsFragment
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var binding: FragmentProductDetailBinding
    private var comesFromSearch = false

    private lateinit var spinnerClassificationQty: Spinner
    private lateinit var spinnerClassification1: Spinner
    private lateinit var spinnerClassification2: Spinner

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.error -> showErrorMsg()
            BR.showPopRating -> showPopUpRating()
            BR.onBack -> setOnBackListener()
            BR.openScreenReviews -> openReviewScreen()
            BR.itemsRelatedProducts -> setRelatedProducts()
            BR.miniSelected -> loadMiniImage(viewModel.miniSelected)
            BR.listClassification1 -> setClassificationSpinner(binding.spinnerClassification1)
            BR.listClassification2 -> setClassificationSpinner(binding.spinnerClassification2)
            BR.listClassification3 -> setClassificationSpinner(binding.spinnerClassification3)
            BR.listClassificationQty -> setClassificationSpinner(binding.spinnerClassificationQty)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        spinnerClassificationQty = binding.spinnerClassificationQty
        spinnerClassification1 = binding.spinnerClassification1
        spinnerClassification2 = binding.spinnerClassification2

        arguments?.let{
            val productId = it.getInt("productId")
            val storeId = it.getInt("storeId")

            comesFromSearch = it.containsKey("comesFromSearch")
            viewModel.initialize(productId, storeId)
        }

        return binding.root
    }

    private fun setRelatedProducts() {
        with(binding.rvRelatedProducts){
            val mAdapter = HomeStoreNewProductsAdapter(requireContext(), viewModel.itemsRelatedProducts)
            adapter = mAdapter

            mAdapter.onItemClicked={ productId->
                val storeId = arguments?.getInt("storeId") ?: 0

                val args = Bundle().apply {
                    putInt("productId", productId)
                    putInt("storeId", storeId)
                }

                requireActivity().openOrRefreshFragment(
                    forStore = true,
                    destination = ProductDetailFragment(),
                    args = args,
                    name = null
                )
            }
        }
    }

    private fun loadMiniImage(imageUrl: String?){
        Glide.with(requireContext())
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(binding.photoView)
    }

    private fun openReviewScreen() {
        val args = Bundle().apply {
            putInt("storeId", viewModel.storeId)
            putInt("productId", viewModel.productId)
        }

        requireActivity().openOrRefreshFragment(
            forStore = true,
            destination = ProductReviewsFragment(),
            args = args,
            name = "Reviews"
        )
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
                toast("Agrege una calificación con las estrellas para continuar")
                return@setOnClickListener
            }

            if(!email.isEmailValid()){
                toast("Ingrese un email válido para continuar")
                return@setOnClickListener
            }

            // everythig goes well -> save on WS
            viewModel.onAddRatingFromPopupClicked(name, email, comments, rate)
            d.dismiss()
        }
    }

    private fun setClassificationSpinner(spinner: Spinner) {

        val list = when(spinner.id){
            spinnerClassificationQty.id -> viewModel.listClassificationQty
            spinnerClassification1.id -> viewModel.listClassification1
            spinnerClassification2.id -> viewModel.listClassification2
            else -> viewModel.listClassification3
        }

        list?.let {
            val mAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_simple, it)
            spinner.adapter = mAdapter

            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                    when(spinner.id){
                        spinnerClassificationQty.id -> {
                            val value = viewModel.listClassificationQty?.get(position)
                            viewModel.valueClassificationSelectedQuantity = value
                        }
                        spinnerClassification1.id -> {
                            val value = viewModel.listClassification1?.get(position)
                            viewModel.valueClassificationSelected1 = value
                        }
                        spinnerClassification2.id -> {
                            val value = viewModel.listClassification2?.get(position)
                            viewModel.valueClassificationSelected2 = value
                        }
                        else -> {
                            val value = viewModel.listClassification3?.get(position)
                            viewModel.valueClassificationSelected3 = value
                        }
                    }
                }
            }
        }
    }

    private fun showErrorMsg(){
        with(viewModel.error){
            val errorType = if(hasErrors) SnackType.ERROR else SnackType.SUCCESS
            showSnackbar(binding.root, viewModel.error.message, errorType)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    @Suppress("DEPRECATION")
    private fun setOnBackListener() {
        if(comesFromSearch) {
            activity?.finish()
        } else {
            activity?.onBackPressed()
        }
    }

    fun itemViewBinder(): ItemBinder<AProductDetailViewModel> {
        return CompositeItemBinder(ProductsDetailItemMiniBinder(BR.viewModel, R.layout.recycler_item_mini_detail))
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}
