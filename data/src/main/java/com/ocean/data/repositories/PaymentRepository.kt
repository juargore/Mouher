package com.ocean.data.repositories

import com.ocean.data.serverapi.PaymentApi
import com.ocean.domain.entities.ParcelData
import com.ocean.domain.entities.RegistrationData
import com.ocean.domain.entities.ResponsePaymentStatus
import com.ocean.domain.repositories.IPaymentRepository
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
        email: String?,
        parcel: ParcelData?
    ): Single<RegistrationData> {

        val alto = if (parcel?.Alto == null || parcel.Alto == 0) "" else parcel.Alto.toString()
        val ancho = if (parcel?.Ancho == null || parcel.Ancho == 0) "" else parcel.Ancho.toString()
        val largo = if (parcel?.Largo == null || parcel.Largo == 0) "" else parcel.Largo.toString()
        val peso = if (parcel?.Peso == null || parcel.Peso == 0.0) "" else parcel.Peso.toString()
        val fragilidad = if (parcel?.Fragilidad == null || parcel.Fragilidad == 0) "" else parcel.Fragilidad.toString()

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
            TipoApp = 2,
            EnvioProveedor = parcel?.Paqueteria ?: "",
            EnvioServicio = parcel?.Servicio ?: "",
            EnvioDescripcion = parcel?.Descripcion ?: "",
            EnvioEstimacionEntrega = parcel?.Estimacion ?: "",
            EnvioDimAlto = alto,
            EnvioDimAncho = ancho,
            EnvioDimLargo = largo,
            EnvioPeso = peso,
            EnvioFragilidad = fragilidad
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