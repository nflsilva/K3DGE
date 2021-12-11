package k3dge.ui

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL.createCapabilities

class Window(
    width: Int,
    height: Int,
    title: String) {

    val id: Long

    init {

        //GLFWErrorCallback.createPrint(Log.e).set()

        if (!glfwInit()) throw IllegalStateException("Unable to initialize GLFW")
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        id = glfwCreateWindow(width, height, title, 0, 0)

        glfwMakeContextCurrent(id)
        glfwSwapInterval(0)
    }
    fun open() {
        createCapabilities()
        glfwShowWindow(id)
    }
    fun close() {
        glfwSetWindowShouldClose(id, true);
    }
    fun isOpen(): Boolean {
        return !glfwWindowShouldClose(id)
    }
    fun onFrame() {
        glfwSwapBuffers(id);
    }
    fun onUpdate() {
        glfwPollEvents();
    }
}