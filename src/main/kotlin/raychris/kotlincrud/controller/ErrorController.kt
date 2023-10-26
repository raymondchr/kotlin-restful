package raychris.kotlincrud.controller

import jakarta.validation.ConstraintViolationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import raychris.kotlincrud.error.NotFoundException
import raychris.kotlincrud.model.WebResponse

@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validationHandler(constraintViolationException: ConstraintViolationException):WebResponse<String>{
        return WebResponse(
            code = 400,
            status = "BAD REQUEST",
            data = "BAD REQUEST"
        )
    }

    @ExceptionHandler(value = [NotFoundException::class])
    fun notFoundHandler(notFoundException: NotFoundException):WebResponse<String>{
        return WebResponse(
            code = 404,
            status = "NOT FOUND",
            data = "DATA NOT FOUND"
        )
    }
}