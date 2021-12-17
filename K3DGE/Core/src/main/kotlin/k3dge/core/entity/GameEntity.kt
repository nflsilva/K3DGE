package k3dge.core.entity

import k3dge.core.common.BaseEntity
import k3dge.core.entity.component.EntityComponent
import k3dge.render.RenderEngine
import k3dge.render.model.ShaderModel
import k3dge.ui.dto.InputStateData
import org.joml.Vector3f

open class GameEntity(position: Vector3f,
                      val rotation: Vector3f,
                      val scale: Vector3f,
                      val shader: ShaderModel): BaseEntity(position) {

    override fun onFrame(graphics: RenderEngine) {
        for(c in components) {
            (c as? EntityComponent)?.onFrame(this, graphics)
        }
    }
}