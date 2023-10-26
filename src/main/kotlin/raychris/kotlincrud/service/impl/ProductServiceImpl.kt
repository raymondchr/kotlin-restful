//package raychris.kotlincrud.service.impl
//
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Pageable
//import org.springframework.stereotype.Service
//import raychris.kotlincrud.entity.Product
//import raychris.kotlincrud.error.NotFoundException
//import raychris.kotlincrud.model.CreateProductRequest
//import raychris.kotlincrud.model.ProductResponse
//import raychris.kotlincrud.model.UpdateProductRequest
//import raychris.kotlincrud.repository.ProductRepository
//import raychris.kotlincrud.service.ProductService
//import raychris.kotlincrud.validation.ValidationUtil
//import java.math.BigDecimal
//import java.util.*
//import java.util.stream.Collector
//import java.util.stream.Collectors
//
//@Service
//class ProductServiceImpl(
//    var validationUtil: ValidationUtil,
//    var productRepository: ProductRepository): ProductService {
//
//    override fun create(createProductRequest: CreateProductRequest): ProductResponse{
//        validationUtil.validate(createProductRequest)
//
//        val product = Product(
//            id = createProductRequest.id,
//            name = createProductRequest.name,
//            description = createProductRequest.description,
//            price = createProductRequest.price,
//            quantity = createProductRequest.quantity
//        )
//
//        productRepository.save(product)
//
//        return product.toResponse()
//    }
//
//    override fun get(id: String): ProductResponse {
//        val product = getIdOrThrowNotFound(id)
//
//        return product.toResponse()
//    }
//
//    override fun getAllTest(pageable: Pageable): Page<ProductResponse> {
//        return productRepository.findByPage(pageable).map { it.toResponse() }
//    }
//
//    override fun update(id: String, updateProductRequest: UpdateProductRequest): ProductResponse {
//        validationUtil.validate(updateProductRequest)
//
//        val product = getIdOrThrowNotFound(id)
//
//        product.apply {
//            name = updateProductRequest.name ?: this.name
//            description = updateProductRequest.description ?: this.description
//            price = updateProductRequest.price ?: BigDecimal.ZERO.toLong()
//            quantity = updateProductRequest.quantity ?: this.quantity
//            updatedAt = Date()
//        }
//
//        productRepository.save(product)
//
//        return product.toResponse()
//    }
//
//    override fun delete(id: String): String{
//        val product = getIdOrThrowNotFound(id)
//
//        productRepository.delete(
//            product
//        )
//
//        return "Delete Success"
//    }
//
//    fun getIdOrThrowNotFound(id: String): Product {
//        return productRepository.findOneById(id) ?: throw NotFoundException("does not exist")
//    }
//}