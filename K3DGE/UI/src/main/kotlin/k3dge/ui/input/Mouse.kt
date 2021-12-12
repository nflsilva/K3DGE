package k3dge.ui.input

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE

class Mouse(window: Long) {

    val pressedButtons: MutableSet<Int> = mutableSetOf()
    var positionX: Int = 0
    var positionY: Int = 0

    init {
        GLFW.glfwSetCursorPosCallback(window) { _: Long, positionX: Double, positionY: Double ->
            onCursorChange(positionX, positionY)
        }
        GLFW.glfwSetMouseButtonCallback(window) { _: Long, button: Int, event: Int, _: Int ->
            onButtonChange(button, event)
        }

    }
    private fun onCursorChange(positionX: Double, positionY: Double) {
        this.positionX = positionX.toInt()
        this.positionY = positionY.toInt()
    }
    private fun onButtonChange(button: Int, action: Int) {
        when(action) {
            GLFW_PRESS -> { pressedButtons.add(button) }
            GLFW_RELEASE -> { pressedButtons.remove(button) }
        }
    }
}