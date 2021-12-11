package k3dge.ui

import org.lwjgl.glfw.GLFW.glfwGetTime

class UIEngine {

    private val window: Window = Window(1280, 720, "Hello LWJGL!")
    private val keyboard: Keyboard = Keyboard(window.id)

    fun getTime(): Double {
        return glfwGetTime()
    }
    fun start() {
        window.open()
    }
    fun stop() {
        window.close()
    }
    fun isRunning(): Boolean {
        return window.isOpen()
    }
    fun getInputState(): InputState {
        return InputState(keyboard.getPressedKeys())
    }
    fun onFrame() {
        window.onFrame()
    }
    fun onUpdate() {
        window.onUpdate()
    }
}