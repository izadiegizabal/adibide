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

    @Test
    fun `selecting a cell should increase row & column values when called`() = runTest {
        // arrange non empty board
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.boardState.collect()
        }

        // act & assert
        sut.selectCell(0 to 0)
        assertEquals(true, sut.boardState.value.board[0].all { value -> value == 1.0 })
        assertEquals(true, sut.boardState.value.board.all { row -> row[0] == 1.0 })
        sut.selectCell(0 to 0)
        assertEquals(true, sut.boardState.value.board[0].all { value -> value == 2.0 })
        assertEquals(true, sut.boardState.value.board.all { row -> row[0] == 2.0 })
    }

    @Test
    fun `selecting a cell should clear row when that row has a fibonacci sequence`() = runTest {
        // arrange non empty board
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.boardState.collect()
        }

        // act
        sut.selectCell(1 to 1)
        sut.selectCell(1 to 2)
        repeat(2) {
            sut.selectCell(1 to 3)
        }
        repeat(3) {
            sut.selectCell(1 to 4)
        }

        // assert
        assertEquals(true, sut.boardState.value.board[0].all { value -> value == 0.0 })
        assertEquals(true, sut.boardState.value.board[1].all { value -> value == 7.0 })
        assertEquals(343.0, sut.boardState.value.score)
    }

    @Test
    fun `selecting a cell should clear column when that row has a fibonacci sequence`() = runTest {
        // arrange non empty board
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.boardState.collect()
        }

        // act
        sut.selectCell(1 to 1)
        sut.selectCell(2 to 1)
        repeat(2) {
            sut.selectCell(3 to 1)
        }
        repeat(3) {
            sut.selectCell(4 to 1)
        }

        // assert
        assertEquals(true, sut.boardState.value.board.all { row -> row[0] == 0.0 })
        assertEquals(true, sut.boardState.value.board.all { row -> row[1] == 7.0 })
        assertEquals(343.0, sut.boardState.value.score)
    }
}
