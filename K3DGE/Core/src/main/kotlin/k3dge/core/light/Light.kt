package k3dge.core.light

import k3dge.core.entity.Entity3D
import k3dge.render.renderer3d.dto.Transform3DData
import org.joml.Vector3f
import org.joml.Vector4f

class Light(position: Vector3f,
            var color: Vector4f): Entity3D(Transform3DData(position)) {
}