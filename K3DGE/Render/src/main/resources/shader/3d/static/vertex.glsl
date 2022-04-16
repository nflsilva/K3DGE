#version 330 core

in vec3 in_position;
in vec2 in_textureCoords;
in vec3 in_normal;

uniform mat4 in_modelMatrix;
uniform mat4 in_viewMatrix;
uniform mat4 in_projectionMatrix;
uniform vec3 in_lightDirection;
uniform mat4 in_lightSpaceMatrix;

out vec2 textureCoords;
out vec3 normal;
out vec3 lightVector;
out vec4 shadowCoords;

void main(){

    vec4 worldPosition = in_modelMatrix * vec4(in_position, 1.0);
    gl_Position = in_projectionMatrix * in_viewMatrix * worldPosition;
    textureCoords = in_textureCoords;

    normal = normalize((in_modelMatrix * vec4(in_normal, 0.0)).xyz);
    lightVector = in_lightDirection;

    shadowCoords = in_lightSpaceMatrix * worldPosition;
};