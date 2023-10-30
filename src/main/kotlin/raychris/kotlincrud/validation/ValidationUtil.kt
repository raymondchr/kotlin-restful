package raychris.kotlincrud.validation

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.stereotype.Component

@Component
class ValidationUtil(var validator: Validator) {
    fun validate(any: Any){
        val result = validator.validate(any)

        if (result.size != 0){
            throw ConstraintViolationException(result)
        }
    }
}