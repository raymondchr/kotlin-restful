package raychris.kotlincrud.model

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class UpdateProductRequest(
    var name: String?,
    var description: String?,
    var price: Long,
    var quantity: Int?
)
