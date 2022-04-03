package k3dge.core.entity.component2d

import k3dge.core.common.BaseComponent
import k3dge.core.common.dto.UpdateData
import k3dge.render.common.model.TextureModel
import k3dge.render.renderer2d.dto.SpriteRenderData
import org.joml.Vector2f

class SpriteEntityComponent(private val texture: TextureModel,
                            private val textureCoords: Array<Vector2f>? = null): BaseComponent() {

    init {
        setUpdateObserver { context -> onUpdate(context) }
        setCleanupObserver { cleanUp() }
    }
    private fun onUpdate(context: UpdateData){
        context.entity?.let { entity ->
            //TODO: This needs to be properly implemented
            context.graphics.renderSprite(
                SpriteRenderData(
                    Vector2f(entity.position.x, entity.position.y),
                    texture.id,
                    textureCoords
                )
            )
        }
    }
    private fun cleanUp(){

    }

}