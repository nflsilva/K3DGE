package k3dge.core

import k3dge.render.RenderEngine
import k3dge.tools.Log

import k3dge.ui.InputState
import k3dge.ui.UIEngine

class CoreEngine {

    private var isRunning: Boolean = false
    private var gameObjects: MutableList<GameEntity> = mutableListOf()
    private val uiEngine: UIEngine = UIEngine()
    private val renderEngine: RenderEngine = RenderEngine()

    private fun run() {

        var nowTime: Double
        var deltaTime: Double
        var lastTime = 0.0
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
                onUpdate(deltaTime, uiEngine.getInputState())
                updates++
                timeToTick = tickTime
            }

            onFrame()
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

        onCleanUp()
    }
    private fun onFrame() {
        uiEngine.onFrame()
        for(go in gameObjects){
            go.onFrame(renderEngine)
        }
    }
    private fun onUpdate(elapsedTime: Double, input: InputState) {
        uiEngine.onUpdate()
        for(go in gameObjects){
            go.onUpdate(elapsedTime, input)
        }
    }
    private fun onCleanUp(){
        for(go in gameObjects){
            go.cleanUp()
        }
    }

    fun start() {
        if(isRunning) return

        uiEngine.start()
        isRunning = true
        run()
    }

    fun addGameObject(gameObject: GameEntity){
        gameObjects.add(gameObject)
    }

    companion object {
        private var ticksPerSecond: Int = 120
    }

}