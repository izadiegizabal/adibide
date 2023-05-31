package xyz.izadi.feature.fibonacci

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.izadi.adibide.feature.fibonacci.FibonacciViewModel
import xyz.izadi.core.testing.MainDispatcherRule
import kotlin.test.assertEquals

class FibonacciViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: FibonacciViewModel

    @Before
    fun `set up`() {
        sut = FibonacciViewModel()
    }

    @Test
    fun `restart should reset the state when called`() = runTest {
        // arrange non empty board
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.boardState.collect()
        }
        sut.selectCell(0 to 0)
        assertEquals(true, sut.boardState.value.board.any { it.any { value -> value != 0.0 } })

        // act
        sut.restart()

        // assert
        assertEquals(true, sut.boardState.value.board.all { it.all { value -> value == 0.0 } })
    }
}
