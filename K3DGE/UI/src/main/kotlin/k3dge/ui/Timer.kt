package k3dge.ui

import org.lwjgl.glfw.GLFW.glfwGetTime

class Timer {
    fun getTime(): Double {
        return glfwGetTime()
    }
}