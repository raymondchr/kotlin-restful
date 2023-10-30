package raychris.kotlincrud.entity

import raychris.kotlincrud.model.ProductResponse

fun Product.toResponse(): ProductResponse {
    return ProductResponse(
        id = requireNotNull(this.id),
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}