package k3dge.core.camera

import k3dge.core.common.BaseEntity
import k3dge.render.RenderEngine
import k3dge.render.dto.CameraRenderData
import org.joml.Vector3f

class GameCamera(position: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
                 var forward: Vector3f = Vector3f(0.0f, 0.0f, -1.0f),
                 var up: Vector3f = Vector3f(0.0f, 1.0f, 0.0f),
                 var lookAt: Vector3f = Vector3f(forward).add(position)) : BaseEntity(position) {

    override fun onFrame(graphics: RenderEngine) {
        graphics.renderCamera(CameraRenderData(position, forward, up, lookAt))
    }

    fun move(direction: Vector3f, delta: Float){
        position.add(Vector3f(direction).mul(delta))
    }
    fun lookForward(){
        lookAt = Vector3f(position).add(forward)
    }
}