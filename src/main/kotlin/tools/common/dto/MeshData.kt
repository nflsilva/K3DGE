package tools.common.dto

data class MeshData(val positions: MutableList<Float> = mutableListOf(),
                    val textureCoordinates: MutableList<Float> = mutableListOf(),
                    val normals: MutableList<Float> = mutableListOf(),
                    val indices: MutableList<Int> = mutableListOf())