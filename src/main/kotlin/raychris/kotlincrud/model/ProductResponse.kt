package raychris.kotlincrud.model

import java.util.*

data class ProductResponse(
    var id: String,
    var name: String,
    var description: String,
    var price: Long,
    var quantity: Int,
    var createdAt: Date,
    var updatedAt: Date?
)
