package raychris.kotlincrud.service.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import raychris.kotlincrud.entity.Product
import raychris.kotlincrud.error.NotFoundException
import raychris.kotlincrud.model.ProductResponse
import raychris.kotlincrud.model.UpdateProductRequest
import raychris.kotlincrud.repository.ProductRepository
import raychris.kotlincrud.validation.ValidationUtil
import java.math.BigDecimal


@ExtendWith(MockitoExtension::class)
class ProductServiceImplTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var validationUtil: ValidationUtil

    @InjectMocks
    private lateinit var productServiceImpl: ProductServiceImpl

    private lateinit var product1: Product
    private lateinit var product2: Product
    private lateinit var product1response: ProductResponse
    private lateinit var product2response: ProductResponse
    private lateinit var products: List<Product>
    private lateinit var productsResponse: List<ProductResponse>
    private lateinit var updateProductRequest1: UpdateProductRequest

    @BeforeEach
    fun beforeEach(){
        this.product1 = Product(
            id = "id",
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 1
        )
        this.product1response = this.product1.toResponse()
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
            name = "name",
            description = "desc update",
            price = 10L,
            quantity = 1
        )


    }

    @Test
    fun dependenciesTest() {
        assertThat(this.productRepository).isNotNull
        assertThat(this.validationUtil).isNotNull
        assertThat(this.productServiceImpl).isNotNull
    }

    @Test
    fun testGetIdNotFound() {
        // Mock the behavior
        whenever(this.productRepository.findOneById(any<String>()))
            .thenReturn(null)

        // Call the under test method
        val exception = assertThrows<NotFoundException> {
            this.productServiceImpl.get("0")
        }

        // assert the result
        assertThat(exception).isInstanceOf(NotFoundException::class.java)
        assertThat(exception.message).isEqualTo("does not exist")

        // Verify the behavior
        verify(this.productRepository, times(1)).findOneById(any<String>())
    }

    @Test
    fun testGetById(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(this.product1)

        val result = productServiceImpl.get("A02")

        assertThat(result).isEqualTo(this.product1response)

        verify(this.productRepository, times(1)).findOneById(any<String>())
    }

//    @Test
//    fun testGetAll(){
//        whenever(productRepository.findAll()).thenReturn(this.products)
//
//        val result = productServiceImpl.getAllTest()
//
//        assertThat(result).isEqualTo(this.productsResponse)
//
//        verify(this.productRepository, times(1)).findAll()
//    }

//    @Test
//    fun testGetAllEmptyList() {
//        whenever(productRepository.findByPage()).thenReturn(emptyList<Product>())
//
//        val result = productServiceImpl.getAllTest()
//
//        assertThat(result).isEqualTo(emptyList<ProductResponse>())
//
//        verify(this.productRepository, times(1)).findAll()
//    }

    @Test
    fun testDelete(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(this.product1)

        val result = productServiceImpl.delete("asd")

        assertThat(result).isEqualTo("Delete Success")
    }

    @Test
    fun testDeleteNotFound(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(null)

        val exception = assertThrows<NotFoundException> {
            this.productServiceImpl.delete("S2")
        }

        assertThat(exception).isInstanceOf(NotFoundException::class.java)
        assertThat(exception.message).isEqualTo("does not exist")

        verify(this.productRepository, times(1)).findOneById(any<String>())
    }

    @Test
    fun testUpdateNoId(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(null)

        val exception = assertThrows<NotFoundException> {
            this.productServiceImpl.update("S2",updateProductRequest1)
        }

        assertThat(exception).isInstanceOf(NotFoundException::class.java)
        assertThat(exception.message).isEqualTo("does not exist")
    }

    @Test
    fun testUpdate(){
        whenever(productRepository.findOneById(any<String>())).thenReturn(this.product1)

        val result = productServiceImpl.update("id", updateProductRequest1)

        this.product1 = Product(
            id = "id",
            name = updateProductRequest1.name ?: product1.name,
            description = updateProductRequest1.description ?: product1.description,
            price = updateProductRequest1.price ?: BigDecimal.ZERO.toLong(),
            quantity = updateProductRequest1.quantity?:product1.quantity
        )

        assertThat(result).isEqualTo(product1.toResponse())
    }
}