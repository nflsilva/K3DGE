#version 330 core

in vec4 color;

out vec4 out_color;

void main()
{
    vec2 temp = gl_PointCoord - vec2(0.5);
    float f = dot(temp, temp);
    if (f>0.25) discard;

    out_color = color;
};