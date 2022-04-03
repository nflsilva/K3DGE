package k3dge.render.common.model

import org.joml.Vector2f
import java.nio.ByteBuffer

open class TextureAtlas(width: Int,
                        height: Int,
                        data: ByteBuffer,
                        private val numberOfRows: Int,
                        private val numberOfColumns: Int): TextureModel(width, height, data) {

    private val sprites: MutableMap<String, Array<Vector2f>> = mutableMapOf()
    private val spriteSize = width / numberOfColumns

    fun setSpriteCoordinates(name: String, row: Int, column: Int) {
        //TODO: Create exception for this
        if(column > numberOfColumns || row > numberOfRows || sprites.keys.contains(name)) { return }

        val spriteLeft = column * spriteSize / width.toFloat()
        val spriteTop = row * spriteSize / height.toFloat()
        val spriteBottom = spriteTop + spriteSize / height.toFloat()
        val spriteRight = spriteLeft + spriteSize / width.toFloat()

        sprites[name] = arrayOf(
            Vector2f(spriteLeft, spriteTop),
            Vector2f(spriteLeft, spriteBottom),
            Vector2f(spriteRight, spriteBottom),
            Vector2f(spriteRight, spriteTop))
    }
    fun getSpriteCoordinates(name: String): Array<Vector2f>?  {
        return sprites[name]
    }

}