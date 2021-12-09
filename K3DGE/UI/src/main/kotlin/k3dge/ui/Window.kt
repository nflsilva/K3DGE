package k3dge.ui

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL.createCapabilities

class Window(
    width: Int,
    height: Int,
    title: String) {

    private val window: Long

    init {

        //GLFWErrorCallback.createPrint(Log.e).set()

        if (!glfwInit()) throw IllegalStateException("Unable to initialize GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        window = glfwCreateWindow(width, height, title, 0, 0)

        glfwSetKeyCallback(window) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true)
            }
        }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
    }

    fun open() {
        createCapabilities()
        glfwShowWindow(window)
    }

    fun close() {
        glfwSetWindowShouldClose(window, true);
    }

    fun isOpen(): Boolean {
        return !glfwWindowShouldClose(window)
    }

    fun onFrame() {
        glfwSwapBuffers(window);
    }

    fun onUpdate() {
        glfwPollEvents();
    }
}