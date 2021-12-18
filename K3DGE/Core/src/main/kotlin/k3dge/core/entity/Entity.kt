package k3dge.core.entity

import k3dge.core.common.BaseEntity
import k3dge.render.RenderEngine
import k3dge.render.model.ShaderModel
import org.joml.Vector3f

class Entity(position: Vector3f,
             val rotation: Vector3f,
             val scale: Vector3f,
             val shader: ShaderModel): BaseEntity(position) {
}