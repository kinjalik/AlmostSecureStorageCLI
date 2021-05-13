package exceptions

class InvalidFileException(private val msg: String): Exception(msg)
