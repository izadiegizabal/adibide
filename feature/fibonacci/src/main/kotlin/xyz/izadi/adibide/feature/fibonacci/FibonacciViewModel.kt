package xyz.izadi.adibide.feature.fibonacci

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import xyz.izadi.adibide.feature.fibonacci.FibonacciBoardConfig.BOARD_SIZE
import xyz.izadi.adibide.feature.fibonacci.FibonacciBoardConfig.MIN_SEQUENCE_SIZE

data class BoardState(
    val board: List<List<Double>> = List(BOARD_SIZE) { List(BOARD_SIZE) { 0.0 } },
    val score: Double = 0.0
)

class FibonacciViewModel : ViewModel() {
    private val _boardState: MutableStateFlow<BoardState> = MutableStateFlow(BoardState())
    val boardState: StateFlow<BoardState> = _boardState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STATE_SHARED_TIMEOUT),
            initialValue = BoardState()
        )

    fun restart() {
        _boardState.update { BoardState() }
    }

    fun selectCell(cell: Pair<Int, Int>) {
        _boardState.update {
            // clear desired consecutive fibonacci numbers in the board & calculate score
            val (updatedBoard, moveScore) = processMove(it.board, cell)

            it.copy(board = updatedBoard, score = it.score + moveScore)
        }
    }

    private fun processMove(
        board: List<List<Double>>,
        selectedCell: Pair<Int, Int>
    ): Pair<List<List<Double>>, Double> {
        // increase by one along the row and column of the selected cell
        //
        // NOTE: the dutch instructions say "worden alle cellen in die rij en kolom met 1 opgehoogd"
        // while the english "all other cells in the same row and column are incremented by 1".
        // i've followed the dutch description, but if the English was followed, we wouldn't increase
        // the value of the selected cell (since it says "all OTHER cells").
        val increasedBoard = board.mapIndexed { i, row ->
            row.mapIndexed { j, value ->
                when {
                    // if we are on the same column or row, as the selected cell, increase the value
                    i == selectedCell.first || j == selectedCell.second -> value + 1
                    else -> value
                }
            }
        }


        // set to keep track of which cells need to be cleared
        val cellsToClear = mutableSetOf<Pair<Int, Int>>()

        // check for desired consecutive fibonacci numbers in any row
        for (i in increasedBoard.indices) {
            for (j in 0..increasedBoard[i].size - MIN_SEQUENCE_SIZE) {
                if (
                    isFibonacciSequence(
                        increasedBoard[i].subList(j, j + MIN_SEQUENCE_SIZE)
                    )
                ) {
                    (0 until MIN_SEQUENCE_SIZE).forEach { offset ->
                        cellsToClear.add(i to j + offset)
                    }
                }
            }
        }

        // check for desired consecutive fibonacci numbers in any column
        for (i in 0..increasedBoard.size - MIN_SEQUENCE_SIZE) {
            for (j in increasedBoard[i].indices) {
                if (isFibonacciSequence(
                        (0 until MIN_SEQUENCE_SIZE).map { offset ->
                            increasedBoard[i + offset][j]
                        }
                    )
                ) {
                    (0 until MIN_SEQUENCE_SIZE).forEach { offset ->
                        cellsToClear.add(i + offset to j)
                    }
                }
            }
        }

        // create a new board with the updated values & count the score
        var moveScore = 0.0
        val processedBoard = increasedBoard.mapIndexed { i, row ->
            row.mapIndexed { j, value ->
                // clearing the cells that we've flagged
                if (cellsToClear.contains(i to j)) {
                    // and increasing the score with the removed values
                    moveScore += increasedBoard[i][j]
                    0.0
                } else {
                    value
                }
            }
        }

        return processedBoard to moveScore
    }

    companion object {
        const val STATE_SHARED_TIMEOUT = 5_000L
    }
}
