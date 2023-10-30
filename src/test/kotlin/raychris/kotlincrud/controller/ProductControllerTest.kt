package raychris.kotlincrud.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.ConstraintViolationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*
import raychris.kotlincrud.entity.Product
import raychris.kotlincrud.entity.toResponse
import raychris.kotlincrud.error.NotFoundException
import raychris.kotlincrud.model.CreateProductRequest
import raychris.kotlincrud.model.UpdateProductRequest
import raychris.kotlincrud.service.ProductService
import raychris.kotlincrud.validation.ValidationUtil

@WebMvcTest(ProductController::class)
@ExtendWith(SpringExtension::class)
internal class ProductControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var productService: ProductService

    private lateinit var product: Product
    private lateinit var createProductRequest1: CreateProductRequest
    private lateinit var createProductRequestBad: CreateProductRequest
    private lateinit var updateProductRequest1: UpdateProductRequest



    @Test
    fun dependenciesTest(){
        assertThat(productService).isNotNull
        assertThat(mockMvc).isNotNull
    }

    @BeforeEach
    fun beforeEach() {
        this.product = Product(
            id = "id",
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 2
        )

        this.createProductRequest1 = CreateProductRequest(
            id = "id",
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 2
        )

        this.createProductRequestBad = CreateProductRequest(
            id = "id",
            name = "",
            description = "desc",
            price = -11,
            quantity = 2
        )

        this.updateProductRequest1 = UpdateProductRequest(
            name = "name",
            description = "desc",
            price = 10L,
            quantity = 2
        )
    }


    @Test
    fun createProductSuccess() {

        whenever(productService.create(createProductRequest1)).thenReturn(product.toResponse())

        mockMvc.post("/api/v1/products"){
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(createProductRequest1)
        }.andDo{
            print()
        }.andExpect {
            status { isCreated() }
            jsonPath("$.code"){value(200)}
            jsonPath("$.status") {value("OK")}
            jsonPath("$.data"){ exists() }
        }
    }

    @Test
    fun createProductFailed() {
       // whenever(validationUtil.validate(any<UpdateProductRequest>())).thenThrow()

        whenever(productService.create(any<CreateProductRequest>())).thenReturn(null)

        mockMvc.post("/api/v1/products"){
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(createProductRequestBad)
        }.andDo{
            print()
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.code"){value(400)}
            jsonPath("$.status"){value("BAD REQUEST")}
            jsonPath("$.data"){value("BAD REQUEST")}
        }
    }

    @Test
    fun getProducts() {
    val expectedPage = PageImpl(listOf(product.toResponse()))

        whenever(productService.getAllTest(any<Pageable>())).thenReturn(expectedPage)

        mockMvc.get("/api/v1/products"){
            contentType = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun getProduct() {
        val productId = "sampleProductId"

        whenever(productService.get(any<String>())).thenReturn(product.toResponse())

        mockMvc.get("/api/v1/products/$productId") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            jsonPath("$.code") { exists() }
            jsonPath("$.status") { exists() }
            jsonPath("$.data"){ exists() }
        }
    }

    @Test
    fun getProductFailed(){
        val productId = "sampleProductId"

        whenever(productService.get(any<String>())).thenReturn(null)

        mockMvc.get("/api/v1/products/$productId") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { NotFoundException("does not exist") }
        }
    }

    @Test
    fun deleteProduct(){
        val productId = "sampleProductId"

        whenever(productService.delete(productId)).thenReturn(null)

        mockMvc.delete("/api/v1/products/$productId"){
            contentType = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            jsonPath("$.data"){value("Deleted")}
        }
    }

    @Test
    fun deleteProductFailed(){
        whenever(productService.delete(any<String>())).thenReturn(null)

        mockMvc.delete("/api/v1/products/2"){
            contentType = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { NotFoundException("does not exist") }
        }
    }

    @Test
    fun updateProduct(){
        whenever(productService.update(any<String>(), any<UpdateProductRequest>())).thenReturn(product.toResponse())

        mockMvc.patch("/api/v1/products/2"){
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(updateProductRequest1)
        }.andDo {
            print()
        }.andExpect {
            jsonPath("$.code") { exists() }
            jsonPath("$.status") { exists() }
            jsonPath("$.data"){ exists() }
        }
    }

    @Test
    fun updateProductFailed(){
        val productId = "sampleId"
        whenever(productService.update(any<String>(), any<UpdateProductRequest>())).thenReturn(null)

        mockMvc.patch("/api/v1/products/$productId"){
            contentType = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { NotFoundException("does not exist") }
        }
    }
}
