package com.glass.data.repositories

import com.glass.data.serverapi.PaymentApi
import com.glass.domain.entities.ProductSearchUI
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponsePaymentStatus
import com.glass.domain.repositories.IPaymentRepository
import io.reactivex.Single

class PaymentRepository(
    private val api: PaymentApi
): IPaymentRepository {

    override fun finalPaymentFirstStep(
        storeId: Int,
        remarks: String,
        clientId: Int,
        subTotalCost: Double,
        shippingCost: Double,
        totalCost: Double,
        requiresBilling: Int,
        rfc: String?,
        socialReason: String?,
        email: String?
    ): Single<RegistrationData> {

        return api.saveSaleFirstStep(
            WebService = "GuardaVenta",
            IdTienda = storeId,
            Observaciones = remarks,
            IdCliente = clientId,
            Subtotal = subTotalCost.toString(),
            CostoEnvio = shippingCost.toString(),
            Total = totalCost.toString(),
            PagoTarjeta = totalCost.toString(),
            FacturaRequerida = requiresBilling,
            RFC = rfc ?: "",
            RazonSocial = socialReason ?: "",
            Correo = email ?: "",
            TipoApp = 2
        )
    }

    override fun finalPaymentSecondStep(
        storeId: Int,
        saleId: Int,
        productId: Int,
        number: Int,
        quantity: Int,
        unitCost: Double,
        totalCost: Double,
        remarks: String
    ): Single<RegistrationData> {

        return api.saveEachSaleSecondStep(
            WebService = "GuardaMovimientoVenta",
            IdTienda = storeId,
            IdVenta = saleId,
            IdProducto = productId,
            Numero = number,
            Cantidad = quantity,
            PrecioUnitario = unitCost.toString(),
            Total = totalCost.toString(),
            Observaciones = remarks
        )
    }

    override fun consultPaymentStatus(
        storeId: String,
        saleId: String
    ): Single<ResponsePaymentStatus> {

        return api.getPaymentStatus(
            WebService = "ConsultaIntegralStatusPagoVenta",
            IdTienda = storeId,
            IdVenta = saleId
        )
    }
}