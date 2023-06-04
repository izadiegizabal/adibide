package xyz.izadi.core.designsystem.component.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.chihsuanwu.freescroll.FreeScrollState

class BoardState(
    val freeScrollState: FreeScrollState,
    val minimapState: MinimapState
) {
    private val _loadContent: MutableState<Boolean> = mutableStateOf(false)
    val loadContent: State<Boolean> = _loadContent

    fun setLoadContent(load: Boolean) {
        _loadContent.value = load
    }
}


@Composable
fun rememberBoardState(
    freeScrollState: FreeScrollState,
    minimapState: MinimapState
) = remember(
    freeScrollState,
    minimapState
) {
    BoardState(
        freeScrollState = freeScrollState,
        minimapState = minimapState
    )
}
