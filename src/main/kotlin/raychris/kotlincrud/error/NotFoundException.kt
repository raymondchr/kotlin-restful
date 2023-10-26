package raychris.kotlincrud.error

import org.springframework.data.crossstore.ChangeSetPersister

class NotFoundException(message: String) : RuntimeException(message)