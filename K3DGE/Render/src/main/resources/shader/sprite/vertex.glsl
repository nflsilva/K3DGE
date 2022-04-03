#version 330 core

in vec2 in_position;
in vec2 in_textureCoords;
in float in_textureIndex;

uniform mat4 in_projectionMatrix;

out vec2 textureCoords;
out float textureIndex;

void main(){
    gl_Position = in_projectionMatrix * vec4(in_position, 0.0, 1.0);
    textureCoords = in_textureCoords;
    textureIndex = in_textureIndex;
};