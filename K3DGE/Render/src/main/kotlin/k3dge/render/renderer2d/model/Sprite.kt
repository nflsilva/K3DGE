package k3dge.render.renderer2d.model

import org.joml.Vector2f

class Sprite(val spriteSize: SpriteSizeEnum,
             val textureId: Int,
             val startTextureCoordinates: Vector2f = Vector2f(0.0F),
             val endTextureCoordinates: Vector2f = Vector2f(1.0F)) {
}