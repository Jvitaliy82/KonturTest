package ru.jdeveloperapps.konturtest.other

sealed class Resourse(
    val message: String? = null
) {
    class Success : Resourse()
    class Error(message: String) : Resourse(message)
    class Loading : Resourse()
}