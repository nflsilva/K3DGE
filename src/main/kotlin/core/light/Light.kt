package core.light

import core.entity.Entity3D
import org.joml.Vector3f
import org.joml.Vector4f
import render.renderer3d.dto.Transform3DData

class Light(
    position: Vector3f,
    var color: Vector4f
) : Entity3D(Transform3DData(position)) {
}