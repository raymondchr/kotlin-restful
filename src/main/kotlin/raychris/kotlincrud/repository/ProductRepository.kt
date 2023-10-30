package raychris.kotlincrud.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import raychris.kotlincrud.entity.Product

@Repository
interface ProductRepository : JpaRepository<Product, String> {

    fun findOneById(id: String?): Product?

    @Query(
        """
           SELECT p
           FROM Product p
           WHERE p.price < 50000 AND p.description <> '2'
        """
    )
    fun findByPage(pageable: Pageable): Page<Product>
}