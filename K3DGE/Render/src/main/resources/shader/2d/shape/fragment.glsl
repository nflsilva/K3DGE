#version 330 core

#define SQUARE 0U
#define CIRCLE 1U
#define DONUT 2U

in vec2 center;
flat in uint type;
in vec4 color;

out vec4 out_color;

void process_circle() {
    float R = 0.5;
    float dist = sqrt(dot(center-0.5f,center-0.5f));
    if (dist >= R){ discard; }
}

void process_donut() {
    float R = 0.5;
    float R2 = 0.25;
    float dist = sqrt(dot(center-0.5f,center-0.5f));
    if (dist >= R || dist <= R2)
    { discard; }
}

void main()
{

    switch(type){
        case SQUARE: {
            // does nothing
            break;
        }
        case CIRCLE: {
            process_circle();
            break;
        }
        case DONUT: {
            process_donut();
            break;
        }
    }
    out_color = color;

};