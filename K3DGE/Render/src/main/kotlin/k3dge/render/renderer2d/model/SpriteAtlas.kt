package k3dge.render.renderer2d.model

import k3dge.render.common.model.Texture
import k3dge.render.renderer2d.enum.SpriteSizeEnum
import org.joml.Vector2f

open class SpriteAtlas(private val texture: Texture,
                       private val numberOfRows: Int,
                       private val numberOfColumns: Int) {

    private val sprites: MutableMap<String, Sprite> = mutableMapOf()

    fun setSprite(name: String, row: Int, column: Int, spriteSize: SpriteSizeEnum) {
        //TODO: Create exception for this
        if(column > numberOfColumns || row > numberOfRows || sprites.keys.contains(name)) { return }

        val spriteLeft = column * spriteSize.value / texture.width.toFloat()
        val spriteTop = row * spriteSize.value / texture.height.toFloat()
        val spriteBottom = spriteTop + spriteSize.value / texture.height.toFloat()
        val spriteRight = spriteLeft + spriteSize.value / texture.width.toFloat()

        sprites[name] = Sprite(
            spriteSize,
            texture.id,
            Vector2f(spriteLeft, spriteTop),
            Vector2f(spriteRight, spriteBottom))
    }
    fun getSprite(name: String): Sprite?  {
        return sprites[name]
    }

}