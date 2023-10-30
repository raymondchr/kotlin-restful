package raychris.kotlincrud.service


import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import raychris.kotlincrud.entity.Product
import raychris.kotlincrud.entity.toResponse
import raychris.kotlincrud.error.NotFoundException
import raychris.kotlincrud.model.CreateProductRequest
import raychris.kotlincrud.model.ProductResponse
import raychris.kotlincrud.model.UpdateProductRequest
import raychris.kotlincrud.repository.ProductRepository
import raychris.kotlincrud.validation.ValidationUtil
import java.util.*

@Service
class ProductService(private var productRepository: ProductRepository,var validationUtil: ValidationUtil) {

    fun create(createProductRequest: CreateProductRequest): ProductResponse{
        validationUtil.validate(createProductRequest)

        val product = Product(
            id = createProductRequest.id,
            name = createProductRequest.name!!,
            description = createProductRequest.description!!,
            price = createProductRequest.price!!,
            quantity = createProductRequest.quantity!!
        )

        productRepository.save(product)

        return product.toResponse()
    }

    fun get(id: String):ProductResponse{
        val product = getIdOrThrowNotFound(id)

        return product.toResponse()
    }

    fun getAllTest(pageable: Pageable): Page<ProductResponse>{
        return productRepository.findByPage(pageable).map { it.toResponse() }
    }

    fun update(id: String, updateProductRequest: UpdateProductRequest): ProductResponse{
        validationUtil.validate(updateProductRequest)

        val product = getIdOrThrowNotFound(id)

        product.apply {
            name = updateProductRequest.name ?: this.name
            description = updateProductRequest.description ?: this.description
            price = updateProductRequest.price
            quantity = updateProductRequest.quantity ?: this.quantity
            updatedAt = Date()
        }

        productRepository.save(product)

        return product.toResponse()
    }

    fun delete(id: String):String{
        val product = getIdOrThrowNotFound(id)

        productRepository.delete(
            product
        )

        return "Delete Success"
    }

    private fun getIdOrThrowNotFound(id: String): Product {
        return productRepository.findOneById(id) ?: throw NotFoundException("does not exist")
    }
}