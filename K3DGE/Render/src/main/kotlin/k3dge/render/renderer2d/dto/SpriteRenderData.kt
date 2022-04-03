package k3dge.render.renderer2d.dto

import k3dge.render.common.model.TextureModel
import org.joml.Vector2f

data class SpriteRenderData(val position: Vector2f,
                            val texture: TextureModel,
                            val texCoords: Array<Vector2f>? = null)