package k3dge.core.light

import k3dge.core.camera.component.CameraComponent
import k3dge.core.common.BaseEntity
import k3dge.core.entity.component.EntityComponent
import k3dge.core.light.component.LightComponent
import k3dge.render.RenderEngine
import k3dge.render.dto.LightRenderData
import org.joml.Vector3f
import org.joml.Vector4f

class GameLight(position: Vector3f,
                var color: Vector4f): BaseEntity(position) {

    override fun onFrame(graphics: RenderEngine) {
        for(c in components) {
            (c as? LightComponent)?.onFrame(this, graphics)
        }
    }
}