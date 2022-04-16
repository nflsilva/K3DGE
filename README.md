# K3DGE
A very simple 3D game engine written in Kotlin

https://gameprogrammingpatterns.com/component.html

<div id="top"></div>
<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">C3DGE</h3>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

C3DGE is an experimental implementation of a 3D Game Engine in C++.

I stopped developing this C++ engine and migrated the code into a Kotlin based solution.

### Built With

This section should list any major libraries used in this project.

* [GLFW](https://www.glfw.org/)
* [OpenGL](https://www.opengl.org/)
* [GLEW](http://glew.sourceforge.net/)
* [GLM](https://glm.g-truc.net/0.9.9/index.html)
* [stb_image](https://github.com/nothings/stb/blob/master/stb_image.h)
* [tiny_obj_loader](https://github.com/tinyobjloader/tinyobjloader)

<!-- GETTING STARTED -->
## Getting Started
### Prerequisites

Install `GLFW`, `GLM` and `GLEW` on the target system.
* Arch Linux
  ```sh
  # pacman -S glfw glm glew
  ```

### Compiling

Compiling C3DGE requires `gcc` and `cmake`.

1. Install the basic tools
* Arch Linux
  ```sh
  # pacman -S gcc cmake
  ```

2. Clone the repo
   ```sh
   $ git clone https://github.com/nflsilva/C3DGE.git
   ```

3. Navigate into 3DGE directory and build the library
   ```sh
   $ cd 3DGE
   $ mkdir build
   $ cd build
   $ cmake ..
   $ make
   ```

<!-- USAGE EXAMPLES -->
## Usage

It is possible to implement custom made game objects by sub-classing the _Base_ engine objects.

To use this engine you need to create a game engine delegate.
Create your game logic class and implement the delegate methods
   ```cpp
   class Game : public CoreEngineDelegate {
      private:
        CoreEngine* engine;
        BaseCamera* camera;
        Tree* tree;
      public:
        Game(CoreEngine* engine) : engine(engine) {}
        ~Game(){};
        void OnStart(){
          // Called when the engine starts.
          // Create your game objects here. 
          camera = new MovingCamera(
            glm::vec3(0, 0, 2), 
            glm::vec3(0, 0,-1), 
            glm::vec3(0, 1, 0));
          engine->SetCamera(camera);

          tree = new Tree(
            glm::vec3(0, 0, 0), 
            glm::vec3(0, 0, 0), 
            glm::vec3(1, 1, 1));
          engine->AddGameObject(tree);

        }
        void OnUpdate(float elapsedTime, InputState input){
          // Called once every game tick.
          // Useful for input processing.
        }
        void OnRender(){
          // Called once every game frame.
        }
        void OnDestroy(){
          // Called when the game stops.
          // Free any objects you needed.
        }
    };
   ```

For more examples, please refer to the [TesterGame Project](https://github.com/nflsilva/C3DGE/tree/master/TesterGame)

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

As guide and study material I would like to acknowledge the following:

* [ThinMatrix YouTube channel](https://www.youtube.com/channel/UCUkRj4qoT1bsWpE_C8lZYoQ)
* [TheBennyBox YouTube channel](https://www.youtube.com/channel/UCnlpv-hhcsAtEHKR2y2fW4Q)


