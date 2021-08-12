# Adventurers' Voyage

## Project Proposal
### Purpose of the application:
The application will run the Adventurers' Voyage game. It will be a **text-based story-driven RPG**, where the user will decide on a character race and profession, and play through a story which can be saved and reloaded at the user's convenience. The game will implement a gui to display the current stats, health, and mana of the character and the enemies they are facing. Additionally, a list of items in the user's inventory will be accessible in the gui. However, the main inputs and outputs of the game will be text-based rather than a world for the user's character to walk around in.
### Intended Users
This application is **intended for people interested in text-based games and RPG adventure games**. The intent is to design a game that has very low entry requirements because of basic input and no time-pressure events. Hopefully, the story will be engaging and long enough that the users feel the need to save and come back to it later.
### Personal Interest:
Adventurers' Voyage is of great interest to myself as it presents a complex environment to develop my programming skills in a fun manner. I enjoy playing RPG's and wrote an extensive list of races and professions, including stats, effects, and descriptions for a paper version of this game; implementing what I have already done into a computer game will be very interesting and rewarding, as I will be able to share the game with friends whom I would not normally be able to meet and play a physical game with.

## User Stories:
- As a user, I want to obtain items and store them in my inventory.
- As a user, I want to be able to see what items are in my inventory.
- As a user, I want to be able to see what stats races and professions have before I choose one.
- As a user, I want to choose as race and profession to play as.
- As a user, I want to engage in combat.
- As a user, I want to be prompted to load a save file when I start the game.
- As a user, I want to know when my game is being saved.

## Phase 4:
### Task 2:
- Robust class (Entity abstract class)
- Bi-directional association (GameState and CombatHandler, AdvVoyGUI and TextInOutGUI and CharacterCreatorGUI)
- ### Task 3:
Although this project was a large challenge, it was also a very rewarding process. However, it forced me to learn many new concepts and implement them for first time, resulting in solutions that worked, but could have been much better. For example, in the GUI version of the game, the observer design pattern could have been used. Making the GameState extend Observable and many of the GUI classes implement Observer, the handleGameState and entire CombatHandler could be greatly simplified. In point form:
- Refactor the GUI updating to use the Observer Design Pattern.