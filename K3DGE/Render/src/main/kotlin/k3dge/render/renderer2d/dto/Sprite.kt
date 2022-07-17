package k3dge.render.renderer2d.dto

import k3dge.render.common.model.Texture
import k3dge.render.renderer2d.model.SpriteSizeEnum
import org.joml.Vector2f

data class Sprite(val spriteSize: SpriteSizeEnum,
                  val texture: Texture,
                  val startTextureCoordinates: Vector2f = Vector2f(0.0F),
                  val endTextureCoordinates: Vector2f = Vector2f(1.0F)) {

    constructor(spriteSize: SpriteSizeEnum,
                textureResource: String,
                startTextureCoordinates: Vector2f = Vector2f(0.0F),
                endTextureCoordinates: Vector2f = Vector2f(1.0F)):
            this(spriteSize, Texture(textureResource), startTextureCoordinates, endTextureCoordinates)
}