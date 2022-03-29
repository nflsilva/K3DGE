package k3dge.core

import k3dge.render.RenderEngine
import k3dge.core.camera.Camera
import k3dge.core.common.dto.UpdateData
import k3dge.configuration.EngineConfiguration
import k3dge.core.entity.Entity
import k3dge.core.light.Light
import k3dge.tools.Log
import k3dge.ui.UIEngine
import k3dge.ui.dto.InputStateData

class CoreEngine(configuration: EngineConfiguration? = null) {

    private var isRunning: Boolean = false
    private var gameObjects: MutableList<Entity> = mutableListOf()
    private var gameLights: MutableList<Light> = mutableListOf()
    private var camera: Camera = Camera()

    private val uiEngine: UIEngine
    private val renderEngine: RenderEngine
    private val configuration: EngineConfiguration
    var delegate: CoreEngineDelegate? = null

    init {
        this.configuration = configuration ?: EngineConfiguration.default()
        renderEngine = RenderEngine(this.configuration)
        uiEngine = UIEngine(this.configuration)
    }

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
                //Log.d("Camera: ${camera.position}")
                ticks = 0
                frames = 0
                timeSincePrint = 0.0
            }
            if(!uiEngine.isRunning()) {
                isRunning = false
            }

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
        renderEngine.onFrame()
    }
    private fun onUpdate(elapsedTime: Double, input: InputStateData) {
        uiEngine.onUpdate()
        renderEngine.onUpdate()
        gameObjects.forEach { o ->
            o.onUpdate(UpdateData(elapsedTime, input, renderEngine, entity = o))
        }
        gameLights.forEach { l ->
            l.onUpdate(UpdateData(elapsedTime, input, renderEngine, light = l))
        }
        camera.onUpdate(UpdateData(elapsedTime, input, renderEngine, camera = camera))
    }
    private fun onCleanUp() {
        gameObjects.forEach { o ->
            o.cleanUp()
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
    fun addEntity(gameObject: Entity){
        gameObjects.add(gameObject)
    }
    fun addEntity(camera: Camera){
        this.camera = camera
    }
    fun addEntity(light: Light){
        gameLights.add(light)
    }

    companion object {
        private var printsPerSecondCap: Int = 1
        private var ticksPerSecondCap: Int = 128
        private var framePerSecondCap: Int = 500
    }
}