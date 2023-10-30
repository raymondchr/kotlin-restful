package raychris.kotlincrud.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import raychris.kotlincrud.entity.Product
import raychris.kotlincrud.entity.toResponse
import raychris.kotlincrud.error.NotFoundException
import raychris.kotlincrud.model.CreateProductRequest
import raychris.kotlincrud.model.ProductResponse
import raychris.kotlincrud.model.UpdateProductRequest
import raychris.kotlincrud.repository.ProductRepository
import raychris.kotlincrud.validation.ValidationUtil


import java.util.*

@ExtendWith(MockitoExtension::class)
internal class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var validationUtil: ValidationUtil

    @InjectMocks
    private lateinit var productService: ProductService

    private lateinit var product1: Product
    private lateinit var product2: Product
    private lateinit var product1response: ProductResponse
    private lateinit var product2response: ProductResponse
    private lateinit var products: List<Product>
    private lateinit var productsResponse: List<ProductResponse>
    private lateinit var updateProductRequest1: UpdateProductRequest
    private lateinit var originalProduct: Product
    private lateinit var expectedProduct: Product
    private lateinit var createProductRequest1: CreateProductRequest

    @BeforeEach
    fun beforeEach() {
        this.product1 = Product(
            id = "id",
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 1
        )
        this.product1response = product1.toResponse()
        this.product2 = Product(
            id = "id2",
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 1
        )
        this.product2response = this.product2.toResponse()
        this.products = listOf(this.product1, this.product2)
        this.productsResponse = listOf(product1response, product2response)
        this.updateProductRequest1 = UpdateProductRequest(
            name = "nameButUpdate",
            description = "descButUpdated",
            price = 10L,
            quantity = 1
        )
        this.originalProduct = Product(
            id = "id",
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 1
        )
        this.expectedProduct = Product(
            id = "id",
            name = "nameButUpdate",
            description = "descButUpdated",
            price = 10L,
            quantity = 1
        )

        this.createProductRequest1 = CreateProductRequest(
            id = "id",
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 1
        )
    }

    @Test
    fun dependenciesTest(){
        assertThat(this.productRepository).isNotNull
        assertThat(this.validationUtil).isNotNull
        assertThat(this.productService).isNotNull
    }

    @Test
    fun createProduct(){

        whenever(productRepository.save(any<Product>())).thenReturn(product1)

        val result = productService.create(createProductRequest1)

        assertThat(result.id).isEqualTo(product1.id)
        assertThat(result.name).isEqualTo(product1.name)
        assertThat(result.description).isEqualTo(product1.description)
        assertThat(result.price).isEqualTo(product1.price)
        assertThat(result.quantity).isEqualTo(product1.quantity)
    }

//    @Test
//    fun createProductFailed(){
//        whenever(productRepository.save(any<Product>())).thenAnswer { invocation -> invocation.arguments[0] as Product }
//
//        val result = productService.create(createProductRequest1)
//
//        assertThat(result).isEqualTo()
//
//    }

    @Test
    fun testGetIdNotFound() {
        // Mock the behavior
        whenever(this.productRepository.findOneById(any<String>()))
            .thenReturn(null)

        // Call the under test method
        val exception = assertThrows<NotFoundException>{
            this.productService.get("0")
        }

        // assert the result
        assertThat(exception).isInstanceOf(NotFoundException::class.java)
        assertThat(exception.message).isEqualTo("does not exist")

        // Verify the behavior
        verify(this.productRepository, times(1)).findOneById(any<String>())
    }


    @Test
    fun testGetById() {
        whenever(productRepository.findOneById(any<String>())).thenReturn(this.product1)

        val result = productService.get("A02")

        assertThat(result).isEqualTo(this.product1response)

        verify(this.productRepository, times(1)).findOneById(any<String>())
    }

    @Test
    fun getAllTest() {
        val expectedPage = PageImpl(listOf(product1))

        val b = PageRequest.of(0, 20)

        whenever(productRepository.findByPage(b)).thenReturn(expectedPage)

        val result = productService.getAllTest(b)

        assertNotNull(result)
    }

    @Test
    fun testDelete() {
        whenever(productRepository.findOneById(any<String>())).thenReturn(this.product1)

        val result = productService.delete("asd")

        assertThat(result).isEqualTo("Delete Success")

        verify(this.productRepository, times(1)).findOneById(any<String>())
    }

    @Test
    fun testDeleteNotFound(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(null)

        val exception = assertThrows<NotFoundException> {
            this.productService.delete("S2")
        }

        assertThat(exception).isInstanceOf(NotFoundException::class.java)
        assertThat(exception.message).isEqualTo("does not exist")

        verify(this.productRepository, times(1)).findOneById(any<String>())
    }

    @Test
    fun testUpdateNoId(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(null)

        val exception = assertThrows<NotFoundException> {
            this.productService.update("S2",updateProductRequest1)
        }

        assertThat(exception.message).isEqualTo("does not exist")

        verify(this.productRepository, times(1)).findOneById(any<String>())
    }

    @Test
    fun testUpdate(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(this.originalProduct)
        whenever(productRepository.save(originalProduct)).thenReturn(expectedProduct)

        val result = productService.update("id", updateProductRequest1)

        expectedProduct.updatedAt = Date()

        assertThat(result.id).isEqualTo(expectedProduct.id)
        assertThat(result.name).isEqualTo(expectedProduct.name)
        assertThat(result.description).isEqualTo(expectedProduct.description)
        assertThat(result.price).isEqualTo(expectedProduct.price)
        assertThat(result.quantity).isEqualTo(expectedProduct.quantity)
        

        verify(this.productRepository, times(1)).findOneById(any<String>())
    }
}