package k3dge.core.camera

import k3dge.core.camera.component.RenderCameraComponent
import k3dge.core.common.BaseEntity
import k3dge.core.entity.component.TransformEntityComponent
import org.joml.Vector3f
import javax.xml.crypto.dsig.Transform

class Camera(position: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
             var forward: Vector3f = Vector3f(0.0f, 0.0f, -1.0f),
             var up: Vector3f = Vector3f(0.0f, 1.0f, 0.0f),
             var lookAt: Vector3f = Vector3f(forward).add(position)) :

    BaseEntity(TransformEntityComponent(position)) {

    init {
        addComponent(RenderCameraComponent())
    }
    fun move(direction: Vector3f, delta: Float){
        transform.position.add(Vector3f(direction).mul(delta))
    }
    fun lookForward(){
        lookAt = Vector3f(transform.position).add(forward)
    }
}