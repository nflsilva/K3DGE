package k3dge.ui

import k3dge.configuration.EngineConfiguration
import k3dge.ui.dto.InputStateData
import k3dge.ui.input.Keyboard
import k3dge.ui.input.Mouse
import org.lwjgl.glfw.GLFW.glfwGetTime

class UIEngine(configuration: EngineConfiguration) {

    private val window: Window = Window(
        configuration.resolutionWidth,
        configuration.resolutionHeight,
        "Hello LWJGL!",
        configuration.enableVsync)
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
    fun getInputState(): InputStateData {
        return InputStateData(keyboard, mouse)
    }
    fun onFrame() {
        window.onFrame()
    }
    fun onUpdate() {
        mouse.onUpdate()
        window.onUpdate()
    }
}