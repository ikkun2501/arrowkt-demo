package com.example

import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import arrow.core.extensions.`try`.monad.binding
import arrow.core.extensions.`try`.monadThrow.bindingCatch
import arrow.core.getOrElse

fun main() {

    // Functor
    val num = Try { "3".toInt() }.map { it + 1 }
    // pattern match
    when (num) {
        is Success -> println(num.value)
        is Failure -> println(num.exception)
    }

    // TODO APPLICANTIVE


    // Monad
    // catch  getOrElse
    bindingCatch {
        val a = "none".toInt()
        val b = "4".toInt()
        val c = "5".toInt()
        a + b + c
    }.getOrElse { 0 }.run(::println)

    // Monad
    // catch  getOrElse 省略
    bindingCatch {
        "none".toInt() + "4".toInt() + "5".toInt()
    }.getOrElse { 0 }.run(::println)

    // Monad
    // binding & fold & success & run
    binding {
        val (a) = Try { "3".toInt() }
        val (b) = Try { "4".toInt() }
        val (c) = Try { "5".toInt() }
        a + b + c
    }.fold(
        { exception: Throwable -> println(exception.message) },
        { number: Int -> println(number) }
    )

    // Monad
    // binding & fold & failure & run
    binding {
        val (a) = Try { "none".toInt() }
        val (b) = Try { "4".toInt() }
        val (c) = Try { "5".toInt() }
        a + b + c
    }.fold(
        { exception: Throwable -> println(exception.message) },
        { number: Int -> println(number) }
    )

    // Monad
    // binding & fold & failure & return
    binding {
        val (a) = Try { "none".toInt() }
        val (b) = Try { "4".toInt() }
        val (c) = Try { "5".toInt() }
        a + b + c
    }.fold(
        { exception: Throwable -> exception.message },
        { number: Int -> number.toString() }
    ).run(::println)

    // try-catch
    try {
        val number = "none".toInt() + "4".toInt() + "5".toInt()
        number.toString()
    } catch (e: Exception) {
        e.message!!
    }.run(::println)

    // Monad
    // binding & fold & failure & return
    bindingCatch {
        val a = "none".toInt()
        val (b) = Try { "4".toInt() }
        val (c) = Try { "5".toInt() }
        a + b + c
    }.fold(
        { exception: Throwable -> exception.message },
        { number: Int -> number.toString() }
    ).run(::println)
}

