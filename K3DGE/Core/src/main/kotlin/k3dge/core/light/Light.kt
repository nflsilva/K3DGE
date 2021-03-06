package k3dge.core.light

import k3dge.core.common.BaseEntity
import k3dge.core.entity.component.TransformEntityComponent
import org.joml.Vector3f
import org.joml.Vector4f

class Light(position: Vector3f,
            var color: Vector4f): BaseEntity(TransformEntityComponent(position)) {
}