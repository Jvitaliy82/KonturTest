package ru.jdeveloperapps.konturtest.other

sealed class Resourse{
    object Success : Resourse()
    data class Error(val content: Event<String>) : Resourse()
    object Loading : Resourse()
}