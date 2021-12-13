package k3dge.core.camera

import k3dge.core.camera.component.CameraComponent
import k3dge.core.common.BaseEntity
import k3dge.core.common.ComponentSignal
import k3dge.tools.Util
import k3dge.ui.dto.InputStateData
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f

class GameCamera(position: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
                 var forward: Vector3f = Vector3f(0.0f, 0.0f, -1.0f),
                 var up: Vector3f = Vector3f(0.0f, 1.0f, 0.0f),
                 var lookAt: Vector3f = Vector3f(forward).add(position)) : BaseEntity(position) {

    fun move(direction: Vector3f, delta: Float){
        position.add(Vector3f(direction).mul(delta))
    }

    fun lookForward(){
        lookAt = Vector3f(position).add(forward)
    }
}