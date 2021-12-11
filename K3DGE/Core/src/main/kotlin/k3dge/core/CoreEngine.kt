package k3dge.core

import k3dge.core.entity.GameEntity
import k3dge.render.RenderEngine
import k3dge.tools.Log

import k3dge.ui.InputState
import k3dge.ui.UIEngine
import java.lang.Double.max

class CoreEngine {

    private var isRunning: Boolean = false
    private var gameObjects: MutableList<GameEntity> = mutableListOf()
    private val uiEngine: UIEngine = UIEngine()
    private val renderEngine: RenderEngine = RenderEngine()
    var delegate: CoreEngineDelegate? = null

    private fun run() {

        var frameStart: Double = 0.0
        var frameEnd: Double = 0.0
        var frameDelta: Double = 0.0

        var frames = 0
        var ticks = 0

        val tickTime: Double = 1.0 / ticksPerSecondCap
        val frameTime: Double = 1.0 / framePerSecondCap
        val printTime: Double = 1.0 / printsPerSecondCap

        var timeSinceTick: Double = 0.0
        var timeSinceFrame: Double = 0.0
        var timeSincePrint: Double = 0.0

        while(isRunning) {

            frameStart = uiEngine.getTime()

            if(timeSinceTick >= tickTime) {
                onUpdate(tickTime, uiEngine.getInputState())
                ticks++
                timeSinceTick = 0.0
            }

            if(timeSinceFrame >= frameTime) {
                onFrame()
                frames++
                timeSinceFrame = 0.0
            }

            if(timeSincePrint >= printTime) {
                Log.d("FramesPerSecond: $frames\t\tFrameTime: ${frameDelta * 1000}\t\tTicks: $ticks")
                ticks = 0
                frames = 0
                timeSincePrint = 0.0
            }
            if(!uiEngine.isRunning()) {
                isRunning = false
            }

            //val timeToSleep = max(frameTime-frameDelta, 0.0) / 2
            //Thread.sleep(0, (timeToSleep * 1000).toInt())

            frameEnd = uiEngine.getTime()
            frameDelta = frameEnd - frameStart
            timeSinceTick += frameDelta
            timeSinceFrame += frameDelta
            timeSincePrint += frameDelta

        }

        onCleanUp()
    }
    private fun onFrame() {
        uiEngine.onFrame()
        for(go in gameObjects){
            go.onFrame(renderEngine)
        }
        renderEngine.onFrame()
    }
    private fun onUpdate(elapsedTime: Double, input: InputState) {
        uiEngine.onUpdate()
        for(go in gameObjects){
            go.onUpdate(elapsedTime, input)
        }
    }
    private fun onCleanUp() {
        for(go in gameObjects){
            go.cleanUp()
        }
    }

    fun start() {
        if(isRunning) return
        uiEngine.start()
        renderEngine.onStart()
        delegate?.onStart()
        isRunning = true
        run()
    }
    fun addGameObject(gameObject: GameEntity){
        gameObjects.add(gameObject)
    }

    companion object {
        private var printsPerSecondCap: Int = 1
        private var ticksPerSecondCap: Int = 128
        private var framePerSecondCap: Int = 500
    }
}