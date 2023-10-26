package raychris.kotlincrud.model

data class WebResponse<T>(
    var code: Int,
    var status: String,
    var data: T
)
