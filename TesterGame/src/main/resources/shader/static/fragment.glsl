#version 330 core

in vec2 textureCoords;
in vec3 normal;
in vec3 lightVector;
in vec4 shadowCoords;

uniform sampler2D texture0;
uniform sampler2D depthMap;
uniform vec4 in_lightColor;

out vec4 out_color;

vec4 processLight(float dotNormalLight){
    float diffuseComponent = max(dotNormalLight, 0.0);
	return diffuseComponent * in_lightColor;
}
float processShadow(float dotNormalLight){
    vec3 fragPosLightSpace = shadowCoords.xyz * 0.5 + 0.5;
    float lightDepth = texture(depthMap, fragPosLightSpace.xy).r;
    float fragDepth = min(fragPosLightSpace.z, 1.0);
    //TODO: I should further improve this mess of a bias computation...
    float bias = max(0.00025 * (1.0 - dotNormalLight), 0.0001);

    return (lightDepth + bias) < fragDepth ? 0.0 : 1.0;
}

void main()
{
    float dotNormalLight = dot(normalize(normal), normalize(lightVector));

    float ambientCoefficient = 0.25;
    vec4 diffuseComponent = processLight(dotNormalLight);
    float shadowCoefficient = processShadow(dotNormalLight);

    vec4 materialColor = texture(texture0, textureCoords);
    vec4 lightColor = in_lightColor;

    vec4 ambientColor = materialColor * ambientCoefficient;
    vec4 diffuseColor = diffuseComponent * materialColor;

    out_color = mix(diffuseColor * shadowCoefficient, ambientColor, ambientCoefficient);
    //out_color = vec4(shadowCoefficient);
};