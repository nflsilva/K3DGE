#version 330 core

in vec2 textureCoords;
in float textureIndex;

uniform sampler2D in_samplers[100];

out vec4 out_color;

void main()
{
    out_color = texture(in_samplers[int(textureIndex)], textureCoords);
};