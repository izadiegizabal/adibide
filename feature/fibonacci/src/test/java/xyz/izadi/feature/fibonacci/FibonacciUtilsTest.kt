package xyz.izadi.feature.fibonacci

import xyz.izadi.adibide.feature.fibonacci.isFibonacciSequence
import kotlin.test.Test
import kotlin.test.assertEquals

class FibonacciUtilsTest {

    @Test
    fun `should return true when checking fibonacci sequence on the base case`() {
        // arrange
        val list = listOf(0.0, 1.0, 1.0, 2.0, 3.0)

        // act
        val isFib = isFibonacciSequence(list)

        // assert
        assertEquals(true, isFib)
    }

    @Test
    fun `should return true when checking fibonacci sequence on any part`() {
        // arrange
        val list = listOf(5.0, 8.0, 13.0, 21.0, 34.0, 55.0)

        // act
        val isFib = isFibonacciSequence(list)

        // assert
        assertEquals(true, isFib)
    }

    @Test
    fun `should return false when checking fibonacci sequence on a list partially fib`() {
        // arrange
        val list = listOf(0.0, 1.0, 1.0, 3.0, 0.0)

        // act
        val isFib = isFibonacciSequence(list)

        // assert
        assertEquals(false, isFib)
    }

    @Test
    fun `should return true when checking fibonacci sequence on a list that is a reversed fib`() {
        // arrange
        val list = listOf(3.0, 2.0, 1.0, 1.0, 0.0)

        // act
        val isFib = isFibonacciSequence(list)

        // assert
        assertEquals(true, isFib)
    }

    @Test
    fun `should return false when checking fibonacci sequence on a list that has an unordered fib`() {
        // arrange
        val list = listOf(0.0, 1.0, 1.0, 3.0, 2.0)

        // act
        val isFib = isFibonacciSequence(list)

        // assert
        assertEquals(false, isFib)
    }

    @Test
    fun `should return false when checking fibonacci sequence on an empty list`() {
        // arrange
        val list = listOf<Double>()

        // act
        val isFib = isFibonacciSequence(list)

        // assert
        assertEquals(false, isFib)
    }
}
