package raychris.kotlincrud.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import raychris.kotlincrud.model.ProductResponse
import java.util.Date

@Entity
@Table(name = "products")
class Product (
    @Id
    val id: String? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "price")
    var price: Long,

    @Column(name = "quantity")
    var quantity: Int,
) {
    @Column(name = "createdAt")
    var createdAt: Date = Date()

    @Column(name = "updatedAt")
    var updatedAt: Date = this.createdAt

    fun toResponse(): ProductResponse {
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
}