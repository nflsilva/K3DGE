package k3dge.ui

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.system.MemoryStack
import java.nio.IntBuffer

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

        glfwSetKeyCallback(window) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true)
            }
        }

        MemoryStack.stackPush().use { stack ->
            val pWidth: IntBuffer = stack.mallocInt(1)
            val pHeight: IntBuffer = stack.mallocInt(1)
            glfwGetWindowSize(window, pWidth, pHeight)
            val videoMode: GLFWVidMode? = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
            videoMode?.let {
                GLFW.glfwSetWindowPos(
                    window,
                    (it.width() - pWidth.get(0)) / 2,
                    (it.height() - pHeight.get(0)) / 2
                )
            }
        }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(0)
    }

    fun open() {
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