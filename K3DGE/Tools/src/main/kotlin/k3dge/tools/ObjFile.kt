package k3dge.tools

import java.io.File

class ObjFile {

    data class Position(val x: Float, val y: Float, val z: Float)
    data class Normal(val x: Float, val y: Float, val z: Float)
    data class TextureCoordinates(val u: Float, val v: Float, var w: Float? = null)
    data class Face(var v: Int, var vt: Int? = null, var vn: Int? = null)

    val positions: MutableList<Position> = mutableListOf()
    val normals: MutableList<Normal> = mutableListOf()
    val textureCoordinates: MutableList<TextureCoordinates> = mutableListOf()
    val faces: MutableList<Face> = mutableListOf()

    private fun addPosition(lineParts: List<String>) {
        if(lineParts.size == 3) {
            positions.add(
                Position(lineParts[0].toFloat(), lineParts[1].toFloat(), lineParts[2].toFloat()))
        }
    }
    private fun addNormal(lineParts: List<String>) {
        if(lineParts.size == 3) {
            normals.add(
                Normal(lineParts[0].toFloat(), lineParts[1].toFloat(), lineParts[2].toFloat()))
        }
    }
    private fun addTextureCoordinate(lineParts: List<String>) {
        if(lineParts.size > 1) {
            val textCoord = TextureCoordinates(lineParts[0].toFloat(), lineParts[1].toFloat())
            if(lineParts.size > 2) textCoord.w = lineParts[2].toFloat()
            textureCoordinates.add(textCoord)
        }
    }
    private fun addFace(lineParts: List<String>) {
        if(lineParts.size == 3) {
            for(faceLine in lineParts) {
                val faceComponents = faceLine.split("/")
                val face = Face(faceComponents[0].toInt())
                if(faceComponents.size > 1) face.vt = faceComponents[1].toInt()
                if(faceComponents.size > 2) face.vn = faceComponents[2].toInt()
                faces.add(face)
            }
        }
    }

    companion object {
        fun loadFromPath(path: String): ObjFile? {
            val file = File(path)
            if(!file.exists()) { return null }
            val result = ObjFile()
            file.readLines().forEach { line->
                val lineParts = line.split(" ")
                if(lineParts.size > 1) {
                    when(lineParts[0]) {
                        "v" -> result.addPosition(lineParts.drop(1))
                        "vn" -> result.addNormal(lineParts.drop(1))
                        "vt" -> result.addTextureCoordinate(lineParts.drop(1))
                        "f" -> result.addFace(lineParts.drop(1))
                    }
                }
            }
            return result
        }
    }
}