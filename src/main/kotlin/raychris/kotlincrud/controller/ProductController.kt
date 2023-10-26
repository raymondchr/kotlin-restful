package raychris.kotlincrud.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import raychris.kotlincrud.model.*
import raychris.kotlincrud.service.ProductService


@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun createProduct(@RequestBody body: CreateProductRequest):WebResponse<ProductResponse>{
        val productResponse = productService.create(body)

        return WebResponse(
            code = 200,
            status = "OK",
            data = productResponse
        )
    }

    @GetMapping
    fun getProduct(pageable: Pageable): Page<ProductResponse> {
        return productService.getAllTest(pageable)
    }

    @GetMapping("/{product_id}")
    fun getProduct(@PathVariable("product_id") id: String):WebResponse<ProductResponse>{
        val productResponse = productService.get(id)

        return WebResponse(
            code = 200,
            status = "OK",
            data = productResponse
        )

    }

    @PutMapping("/{product_id}")
    fun updateProduct(@PathVariable("product_id") id: String, @RequestBody body: UpdateProductRequest):WebResponse<ProductResponse>{
        val productResponse = productService.update(id,body)

        return WebResponse(
            code = 200,
            status = "OK",
            data = productResponse
        )
    }

    @DeleteMapping("/{product_id}")
    fun deleteProduct(@PathVariable("product_id") id: String):WebResponse<String>{
        val productResponse = productService.delete(id)

        return WebResponse(
            code = 200,
            status = "OK",
            data = productResponse
        )
    }
}