package core.camera

import core.camera.component.RenderCameraComponent
import core.entity.Entity3D
import org.joml.Vector3f
import render.renderer3d.dto.Transform3DData

class Camera(
    position: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
    var forward: Vector3f = Vector3f(0.0f, 0.0f, -1.0f),
    var up: Vector3f = Vector3f(0.0f, 1.0f, 0.0f),
    var lookAt: Vector3f = Vector3f(forward).add(position)
) :
    Entity3D(Transform3DData(position)) {

    init {
        addComponent(RenderCameraComponent())
    }

    fun move(direction: Vector3f, delta: Float) {
        transform.translate(Vector3f(direction).mul(delta))
    }

    fun lookForward() {
        val currentPosition = transform.getPos()
        lookAt = Vector3f(
            currentPosition.x,
            currentPosition.y,
            currentPosition.z
        ).add(forward)
    }
}