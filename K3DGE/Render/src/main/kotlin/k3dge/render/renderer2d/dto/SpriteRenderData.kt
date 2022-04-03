package k3dge.render.renderer2d.dto

import org.joml.Vector2f

data class SpriteRenderData(val position: Vector2f,
                            val textureId: Int,
                            val texCoords: Array<Vector2f>? = null)