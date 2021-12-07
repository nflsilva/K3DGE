package k3dge.core

import k3dge.tools.Log

import k3dge.ui.InputState
import k3dge.ui.UIEngine


class CoreEngine {

    private var isRunning: Boolean = false
    private var ticksPerSecond: Int = 120

    private val uiEngine: UIEngine = UIEngine()

    fun start() {
        if(isRunning) return

        uiEngine.start()
        isRunning = true
        run()
    }
    fun stop() {
        uiEngine.stop()
    }

    private fun run() {

        var nowTime = 0.0
        var lastTime = 0.0
        var deltaTime = 0.0
        var timeToTick = 0.0

        var frames = 0
        var updates = 0
        var seconds = 0

        val tickTime: Double = 1.0 / ticksPerSecond

        while(isRunning) {

            nowTime = uiEngine.getTime()
            deltaTime = (nowTime - lastTime);
            timeToTick -= deltaTime;
            lastTime = nowTime;

            if(timeToTick <= 0) {
                update(deltaTime, uiEngine.getInputState())
                updates++
                timeToTick = tickTime
            }

            render()
            frames++

            if(uiEngine.getTime() > seconds) {
                Log.d("$frames $deltaTime $updates")
                updates = 0
                frames = 0
                seconds++
            }

            if(!uiEngine.isRunning()) {
                isRunning = false
            }
        }
    }
    private fun render() {
        uiEngine.onFrame()
    }
    private fun update(elapsedTime: Double, input: InputState) {
        uiEngine.onUpdate()
    }

}