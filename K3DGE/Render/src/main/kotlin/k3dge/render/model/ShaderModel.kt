package k3dge.render.model

import k3dge.tools.Log
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*

class ShaderModel(vertexSource: String, fragmentSource: String) {

    val programId: Int = glCreateProgram()
    private val shaders: MutableList<Int> = mutableListOf()
    private val uniforms: MutableMap<String, Int> = mutableMapOf()

    init {
        addShader(vertexSource, GL_VERTEX_SHADER)
        addShader(fragmentSource, GL_FRAGMENT_SHADER)
        linkProgram()

        bindAttribute(0, POSITION_ATTRIBUTE);
        bindAttribute(1, TEXTCOORDS_ATTRIBUTE);
        bindAttribute(2, NORMAL_ATTRIBUTE);

        addUniform(MODEL_MATRIX_UNIFORM)
        addUniform(VIEW_MATRIX_UNIFORM)
        addUniform(PROJECTION_MATRIX_UNIFORM)
    }
    fun bind(){
        glUseProgram(programId);
    }
    fun unbind(){
        glUseProgram(0);
    }
    fun bindAttribute(attribute: Int, variableName: String){
        glBindAttribLocation(programId, attribute, variableName);
    }
    fun addUniform(name: String){
        if(uniforms.containsKey(name)) return
        val location: Int = glGetUniformLocation(programId, name)
        if(location == GL_INVALID_VALUE || location == GL_INVALID_OPERATION || location == GL_INVALID_OPERATION){
            Log.e("Failed to create \"$name\" uniform.");
            return
        }
        uniforms[name] = location
    }
    fun setUniformi(name: String, value: Int){
        uniforms[name]?.let {
            glUniform1i(it, value)
        }
    }
    fun setUniformf(name: String, value: Float){
        uniforms[name]?.let {
            glUniform1f(it, value)
        }
    }
    fun setUniform3f(name: String, value: Vector3f){
        uniforms[name]?.let {
            glUniform3f(it, value.x, value.y, value.z)
        }
    }
    fun setUniform4f(name: String, value: Vector4f){
        uniforms[name]?.let {
            glUniform4f(it, value.x, value.y, value.z, value.w)
        }
    }
    fun setUniformMatrix4f(name: String, value: Matrix4f){
        uniforms[name]?.let {
            val data = BufferUtils.createFloatBuffer(4*4)
            glUniformMatrix4fv(it, false, value.get(data))
        }
    }
    fun setModelMatrix(value: Matrix4f){
        setUniformMatrix4f(MODEL_MATRIX_UNIFORM, value)
    }
    fun setViewMatrix(value: Matrix4f){
        setUniformMatrix4f(VIEW_MATRIX_UNIFORM, value)
    }
    fun setProjectionMatrix(value: Matrix4f){
        setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, value)
    }
    fun setLightDirection(value: Vector3f){
        setUniform3f(LIGHT_DIRECTION_UNIFORM, value)
    }
    fun setLightColor(value: Vector4f){
        setUniform4f(LIGHT_COLOR_UNIFORM, value)
    }

    private fun addShader(sourceCode: String, type: Int){

        val shader = glCreateShader(type)
        if(shader == 0){
            Log.e("Error creating shader")
            return
        }
        glShaderSource(shader, sourceCode)

        val compileSuccess = BufferUtils.createIntBuffer(1)
        glCompileShader(shader);
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileSuccess)
        if(compileSuccess.get() != GL_TRUE){
            val message = glGetShaderInfoLog(shader)
            Log.e(message)
            return
        }

        glAttachShader(programId, shader)
        shaders.add(shader)
    }
    private fun linkProgram(){

        var success = BufferUtils.createIntBuffer(1)
        glLinkProgram(programId);
        glGetProgramiv(programId, GL_LINK_STATUS, success)
        if(success.get() != GL_TRUE){
            val message = glGetProgramInfoLog(programId)
            Log.e(message)
            return
        }

        success = BufferUtils.createIntBuffer(1)
        glValidateProgram(programId);
        glGetProgramiv(programId, GL_VALIDATE_STATUS, success)
        if(success.get() != GL_TRUE) {
            val message = glGetProgramInfoLog(programId)
            Log.e(message)
            return
        }
    }

    companion object {
        const val POSITION_ATTRIBUTE = "in_position"
        const val TEXTCOORDS_ATTRIBUTE = "in_texCoord"
        const val NORMAL_ATTRIBUTE = "in_normal"

        const val MODEL_MATRIX_UNIFORM = "in_modelMatrix"
        const val VIEW_MATRIX_UNIFORM = "in_viewMatrix"
        const val PROJECTION_MATRIX_UNIFORM = "in_projectionMatrix"
        const val LIGHT_DIRECTION_UNIFORM = "in_lightDirection"
        const val LIGHT_COLOR_UNIFORM = "in_lightColor"
    }

}