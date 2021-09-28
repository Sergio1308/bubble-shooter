# bubble-shooter
Java casual 2D-game with shooter elements, using Swing

The game was created for educational and entertainment purposes to get practice.

Mastered and done:
* Swing library, using basic methods to create a game;
* Creation of GUI elements using Swing
* Creation of mathematical regularities of the trajectory of movement of objects using Java
* OOP principles

![screen](https://i.imgur.com/h81Tjzq.png)

# Essence and rules:

### Goal 
score as many points as possible. Points are awarded for killing enemies. 

### Game mechanics
the game screen contains the player and opponents who move from top to bottom towards the player, pushing off the walls. Spawn of opponents on the playing field is presented in the form of waves. All opponents are classified by ranks. With each new wave, the number of opponents increases, and their rank also changes. Each rank has their features and properties: size, color, health, damage when touching the player. 

In addition, such ranks of enemies have been implemented, which, when killed by the player, are divided into smaller enemies.

### Conditions to end the game
the player has only three lives. When a player collides with an enemy object, he will lose one or more lives (depends on the rank of the enemy). With the loss of all lives, the game ends.

### Control mechanics 
using the keyboard, the player can control an object on the playing field. Using the mouse, you can shoot at enemies.

### Shooting mechanics 
there is powerups that can drop from destroyed opponents with a small chance. When the player picks up a certain number of powerups, it changes the shooting mechanics and thereby helps the player to kill more enemies. As soon as the player collects the required amount of experience, a new type of shooting will open to him. With each new wave passed, there will be more and more opponents, so the player needs such a mechanic.

# Project structure
"src" folder contains all java classes for game functionality.
The launch of the game is implemented in the "GameStart.java" class.
The main mechanics and properties of the game are implemented in the "GamePanel.java" class.

"image" folder contains bitmaps for the background of the game windows.
