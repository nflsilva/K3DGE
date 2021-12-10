package k3dge.ui

class UIEngine {

    private val window: Window = Window(1280, 720, "Hello LWJGL!")
    private val timer: Timer = Timer()

    fun getTime(): Double {
        return timer.getTime()
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
        //TODO: Implement User Input
        return InputState(arrayOf())
    }
    fun onFrame() {
        window.onFrame()
    }
    fun onUpdate() {
        window.onUpdate()
    }
}