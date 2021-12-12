package k3dge.ui

import k3dge.ui.dto.InputState
import k3dge.ui.input.Keyboard
import k3dge.ui.input.Mouse
import org.lwjgl.glfw.GLFW.glfwGetTime

class UIEngine {

    private val window: Window = Window(1280, 720, "Hello LWJGL!")
    private val keyboard: Keyboard = Keyboard(window.id)
    private val mouse: Mouse = Mouse(window.id)

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
        return InputState(
            keyboard.pressedKeys,
            mouse.pressedButtons,
            mouse.positionX,
            mouse.positionY)
    }
    fun onFrame() {
        window.onFrame()
    }
    fun onUpdate() {
        window.onUpdate()
    }
}