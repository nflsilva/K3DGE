package k3dge.render

import org.joml.Vector3f

class Camera(
    private var position: Vector3f,
    private var forward: Vector3f,
    private var up: Vector3f) {

    init {
        position = position.normalize()
        forward = forward.normalize()
        up = up.normalize()
    }


}