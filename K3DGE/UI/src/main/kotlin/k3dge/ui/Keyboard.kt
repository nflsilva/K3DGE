package k3dge.ui

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback

class Keyboard(window: Long) {

    private val pressedKeys: MutableSet<Int> = mutableSetOf()

    init {
        glfwSetKeyCallback(window) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (action == GLFW.GLFW_PRESS) {
                pressedKeys.add(key)
            }
            else if(action == GLFW.GLFW_RELEASE) {
                pressedKeys.remove(key)
            }
        }
    }

    fun getPressedKeys(): Set<Int> {
        return pressedKeys
    }

    companion object {



    }
}