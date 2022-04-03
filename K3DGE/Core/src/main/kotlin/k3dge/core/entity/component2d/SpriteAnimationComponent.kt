package k3dge.core.entity.component2d

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.render.renderer2d.dto.SpriteRenderData
import org.joml.Vector2f

class SpriteAnimationComponent(): BaseComponent()  {

    private var currentState: String? = null
    private var currentKeyframeIndex: Int = 0
    private var currentKeyframeElapsedTime: Double = 0.0
    private val keyframesByState: MutableMap<String, MutableList<SpriteKeyframe>> = mutableMapOf()

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){

        if(currentState == null) { return }

        val currentStateKeyframes = keyframesByState[currentState]?: return
        val currentKeyframe = currentStateKeyframes[currentKeyframeIndex % currentStateKeyframes.size]

        context.entity?.let { entity ->
            context.graphics.renderSprite(
                SpriteRenderData(
                    Vector2f(entity.position.x, entity.position.y),
                    currentKeyframe.textureId,
                    currentKeyframe.textureCoords
                )
            )
        }

        currentKeyframeElapsedTime += context.elapsedTime
        if(currentKeyframe.time < currentKeyframeElapsedTime){
            currentKeyframeIndex++
            currentKeyframeElapsedTime = 0.0
        }

    }
    private fun cleanUp(){

    }

    fun addStateKeyframes(state: String, keyframes: MutableList<SpriteKeyframe>) {
        keyframesByState[state] = keyframes
    }

    fun setState(state: String) {
        currentState = state
    }

    data class SpriteKeyframe(val textureId: Int,
                              val textureCoords: Array<Vector2f>?,
                              val time: Double)

}