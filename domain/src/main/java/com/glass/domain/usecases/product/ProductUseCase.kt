package com.glass.domain.usecases.product

import com.glass.domain.entities.*
import com.glass.domain.repositories.IPaymentRepository
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable
import io.reactivex.Single

class ProductUseCase(
    private val productRepository: IProductRepository,
    private val paymentRepository: IPaymentRepository
): IProductUseCase {

    private var productByCategoryData: ProductByCategoryData? = null
    private var fullProductDataResponse: FullProductDataResponse? = null

    override fun triggerToGetFullProduct(storeId: Int, productId: Int): Observable<Unit> {
        return productRepository.triggerToGetFullProduct(storeId, productId).map {
            fullProductDataResponse = it
        }
    }

    override fun getProductUI(): Observable<ProductUI> {
        return Observable.just(fullProductDataResponse?.getProductUI())
    }

    override fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<List<ProductUI>> {
        val mList = mutableListOf<ProductUI>()

        return productRepository.getProductListByCategory(storeId, categoryId)
            .map { data->
                productByCategoryData = data

                data.Productos?.forEach {
                    mList.add(it.toProductUI())
                }
                return@map mList
            }
    }

    override fun getTopScreenInformation(fromDetail: Boolean): Observable<ScreenTopInformationUI> {
        return if(fromDetail) {
            Observable.just(fullProductDataResponse?.getTopInformation())
        } else {
            Observable.just(productByCategoryData?.getScreenTopInfo())
        }
    }

    override fun getReviewsForProduct(storeId: Int, productId: Int): Single<List<ReviewUI>> {
        return productRepository.triggerToGetFullProduct(storeId, productId)
            .map { response->
                val mList = mutableListOf<ReviewUI>()

                response.Reseñas?.forEach {
                    mList.add(it.toReviewUI())
                }
                return@map mList.toList()
        }.firstOrError()
    }

    override fun saveNewReviewForProduct(
        storeId: Int,
        productId: Int,
        userName: String,
        userEmail: String,
        userComment: String,
        userRating: Float
    ): Observable<ResponseUI> {
        return productRepository.saveNewReviewForProduct(storeId, productId, userName, userEmail, userComment, userRating)
            .map { it.toResponseUI() }
    }

    override fun getHistoryByUser(userId: Int, startDate: String): Single<List<HistoryUI>> {
        return productRepository.getHistoryForUser(userId, startDate)
            .map { response ->
                val mList = mutableListOf<HistoryUI>()
                response.Ventas?.forEach {
                    mList.add(it.getHistoryUI())
                }

                return@map mList
            }
    }

    override fun makePaymentOfProducts(
        storeId: Int,
        remarks: String,
        clientId: Int,
        subTotalCost: Double,
        shippingCost: Double,
        totalCost: Double,
        requiresBilling: Int,
        rfc: String?,
        socialReason: String?,
        email: String?,
        products: List<Item>,
        parcel : ParcelData?
    ): Single<RegistrationData> {

        return paymentRepository.finalPaymentFirstStep(
            storeId = storeId,
            remarks = remarks,
            clientId = clientId,
            subTotalCost = subTotalCost,
            shippingCost = shippingCost,
            totalCost = totalCost,
            requiresBilling = requiresBilling,
            rfc = rfc,
            socialReason = socialReason,
            email = email,
            parcel = parcel
        ).map { result ->

            if (result.Error > 0) {
                // something went wrong when processing first step of payment process
                return@map RegistrationData(Error = 1, Mensaje = result.Mensaje)
            } else {
                // it looks that everythig went well on first step, verify the saleId > 0
                val saleId = result.Id ?: 0

                if (saleId > 0) {
                    // now call every function to inform to our Server
                    products.forEachIndexed { index, item ->
                        val response = makePaymentForEachProduct(
                            storeId = storeId,
                            saleId = saleId,
                            productId = item.id!!,
                            counter = (index+1),
                            quantity = item.quantity!!,
                            unitCost = item.price!!,
                            totalCost = (item.price!! * item.quantity!!),
                            remarks = item.valueClassification!!
                        )

                        // if at least one error occurs, return the error to viewModel
                        if(response.Error > 0) {
                            return@map RegistrationData(Error = 1, Mensaje = response.Mensaje)
                        }
                    }

                    // everything went well -> return the saleId value needed on the viewModel
                    return@map RegistrationData(Error = 0, Mensaje = "Su compra ha sido registrada. Por favor, proceda con el pago con tarjeta. La información de su tarjeta bancaría no será almacenada.", Id = saleId)

                } else {
                    // the process was success but for some reason the saleId is 0 -> inform to viewModel
                    return@map RegistrationData(Error = 1,
                        Mensaje = "El Id de la venta es igual a 0. Intente nuevamente o contacte al servicio técnico."
                    )
                }
            }
        }
    }

    override fun getPaymentStatus(storeId: String, saleId: String): Single<ResponsePaymentStatus> {
        return paymentRepository.consultPaymentStatus(storeId, saleId)
    }

    override fun getSearchResults(word: String): Single<List<ProductSearchUI>> {
        return productRepository.getSearchResults(word).map { response ->
            val mList = mutableListOf<ProductSearchUI>()
            response.Productos?.forEach { mList.add(it.toProductSearchUI()) }
            return@map mList
        }
    }

    override fun getParcelsPrices(storeId: Int, clientId: Int, productsIds: List<Int>, quantities: List<Int>): Single<ParcelsResponse> {
        return productRepository.getParcelsPrices(storeId, clientId, productsIds, quantities)
    }

    private fun makePaymentForEachProduct(
        storeId: Int,
        saleId: Int,
        productId: Int,
        counter: Int,
        quantity: Int,
        unitCost: Double,
        totalCost: Double,
        remarks: String
    ): RegistrationData{
        return paymentRepository.finalPaymentSecondStep(
            storeId, saleId, productId, counter, quantity, unitCost, totalCost, remarks
        ).blockingGet()
    }

    override fun getRelatedProductsByProduct(): Observable<List<ProductUI>> {
        val mList = mutableListOf<ProductUI>()

        fullProductDataResponse?.ProductosOfrecidos?.forEach {
            mList.add(it.toProductUI())
        }

        return Observable.just(mList)
    }

}