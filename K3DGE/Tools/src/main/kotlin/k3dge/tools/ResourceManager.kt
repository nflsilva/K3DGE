package k3dge.tools

import k3dge.tools.dto.MeshData
import k3dge.tools.dto.ShaderData
import k3dge.tools.dto.TextureData
import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage.stbi_load
import java.nio.ByteBuffer
import java.nio.IntBuffer


object ResourceManager {

    private val textures: MutableMap<String, TextureData> = mutableMapOf()
    private val meshes: MutableMap<String, MeshData> = mutableMapOf()
    private val shaders: MutableMap<String, ShaderData> = mutableMapOf()

    fun loadTextureFromFile(fileName: String): TextureData? {
        if(fileName in textures.keys) { return textures[fileName] }
        getResourceURLFromFile(fileName)?.let { resourcePath ->
            val width: IntBuffer = BufferUtils.createIntBuffer(1)
            val height: IntBuffer = BufferUtils.createIntBuffer(1)
            val components: IntBuffer = BufferUtils.createIntBuffer(1)
            stbi_load(resourcePath, width, height, components, 4)?.let { data ->
                val textureData = TextureData(width.get(), height.get(), components.get(), data)
                textures[fileName] = textureData
                return textureData
            }
        }
        return null
    }
    fun loadMeshFromFile(fileName: String): MeshData? {
        if(fileName in meshes.keys) { return meshes[fileName] }
        getResourceURLFromFile(fileName)?.let { resourcePath ->
            ObjFile.loadFromPath(resourcePath)?.let { parsedObj ->
                val meshData = MeshData()
                var faceIndex = 0
                parsedObj.faces.forEach { face->
                    val vertex = parsedObj.positions[face.v - 1]
                    meshData.vertices.add(vertex.x)
                    meshData.vertices.add(vertex.y)
                    meshData.vertices.add(vertex.z)
                    face.vn?.let { vn->
                        val normal = parsedObj.normals[vn - 1]
                        meshData.normals.add(normal.x)
                        meshData.normals.add(normal.y)
                        meshData.normals.add(normal.z)
                    }
                    face.vt?.let { vt->
                        val textureCoordinate = parsedObj.textureCoordinates[vt - 1]
                        meshData.textureCoordinates.add(textureCoordinate.u)
                        meshData.textureCoordinates.add(textureCoordinate.v)
                    }
                    meshData.indices.add(faceIndex++)
                }
                meshes[fileName] = meshData
                return meshData
            }
        }
        return null
    }
    fun loadShaderSourceFromFile(fileName: String): ShaderData? {
        if(fileName in shaders.keys) { return shaders[fileName] }
        getResourceURLFromFile(fileName)?.let { resourcePath ->
            object{}::class.java.getResource(resourcePath)?.let { data ->
                val shaderData = ShaderData(data.readText())
                shaders[fileName] = shaderData
                return shaderData
            }
        }
        return null
    }

    private fun getResourceURLFromFile(fileName: String): String? {
        object{}::class.java.getResource(fileName)?.let { url ->
            var resourcePath = url.path
            //Why do I need this hack in windows?
            //resourcePath = resourcePath.drop(1)
            return resourcePath
        }
        Log.e("Error loading resource $fileName.")
        return null
    }
}