package com.ocean.domain.entities

data class HistoryResponse(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Ventas: List<HistoryData>? = null
)

data class HistoryData(
    val Tienda: String? = null,
    val Fecha: String? = null,
    val Folio: String? = null,
    val Id: Int? = null,
    val Envio: String? = null,
    val Subtotal: String? = null,
    val Total: String? = null,
    val Productos: String? = null,
    val StatusPago: String? = null,
    val ColorStatusPago: String? = null,
    val StatusEnvío: String? = null,
    val ColorStatusEnvío: String? = null
){
    fun getHistoryUI() = HistoryUI(
        id = Id,
        storeName = Tienda,
        date = Fecha,
        folio = Folio,
        shippingFee = Envio,
        subTotalAmount = Subtotal,
        totalAmount = Total,
        paymentStatus = StatusPago,
        colorPaymentStatus = ColorStatusPago,
        shipmentStatus = StatusEnvío,
        colorShipmentStatus = ColorStatusEnvío,
        urlForDetails = Productos
    )
}


data class HistoryUI(
    val id: Int? = null,
    val storeName: String? = null,
    val date: String? = null,
    val folio: String? = null,
    val totalAmount: String? = null,
    val subTotalAmount: String? = null,
    val shippingFee: String? = null,
    val paymentStatus: String? = null,
    val colorPaymentStatus: String? = null,
    val shipmentStatus: String? = null,
    val colorShipmentStatus: String? = null,
    val urlForDetails: String? = null
)