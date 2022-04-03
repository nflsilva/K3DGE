package k3dge.render.common.model

import org.joml.Vector2f
import java.nio.ByteBuffer

open class TextureAtlas(width: Int,
                   height: Int,
                   data: ByteBuffer,
                   private val size: Int): TextureModel(width, height, data) {

    private val sprites: MutableMap<String, Pair<Float, Float>> = mutableMapOf()
    private val spriteSize: Float = 1F / size

    fun setSpriteCoordinates(name: String, column: Int, row: Int) {
        //TODO: Create exception for this
        if(column > size || row > size || sprites.keys.contains(name)) { return }
        sprites[name] = Pair(column * spriteSize, size - row * spriteSize)
    }
    fun getSpriteCoordinates(name: String): Array<Vector2f>?  {
        sprites[name]?.let {
            val subSpriteV = it.second      //Vertical
            val subSpriteU = it.first       //Horizontal
            return arrayOf(
                Vector2f(subSpriteU, subSpriteV),
                Vector2f(subSpriteU, subSpriteV + spriteSize),
                Vector2f(subSpriteU + spriteSize, subSpriteV + spriteSize),
                Vector2f(subSpriteU + spriteSize, subSpriteV))
        }
        return null
    }

}