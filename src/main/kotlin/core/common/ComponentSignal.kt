package core.common

import java.util.*

class ComponentSignal(
    val senderId: UUID,
    val type: Type
) {
    enum class Type(value: Int) {
        STOP_ROTATION(0)
    }
}