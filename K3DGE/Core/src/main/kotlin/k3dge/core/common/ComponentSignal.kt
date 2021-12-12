package k3dge.core.common

class ComponentSignal(
    val senderId: String,
    val type: Type) {

    enum class Type(value: Int) {
        STOP_ROTATION(0)
    }
}