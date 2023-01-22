package exception

class NoSuchGameException(override val message: String?): Exception(message) {
}