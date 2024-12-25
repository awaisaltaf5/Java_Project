Tic-Tac-Toe Game with Java Swing

Overview

This project is a Tic-Tac-Toe Game developed in Java using Java Swing for the GUI. It incorporates object-oriented programming (OOP) principles, such as encapsulation, inheritance, abstraction, and polymorphism, making it a comprehensive demonstration of Java's OOP capabilities.

The game provides two modes:

1 vs 1: Two players can play on the same device.

1 vs Computer: Play against an AI opponent.

The game features a vibrant user interface, background music, and various interactive elements to enhance the user experience.

Features

Login and Registration:

Users can register and log in to play the game.

Credentials are securely stored in a text file.

Password update functionality.

Game Modes:

1 vs 1 mode: Two players compete against each other.

1 vs Computer mode: AI makes random moves.

Interactive GUI:

Dynamic, responsive UI with a grid-based game board.

Intuitive design with colorful buttons and labels.

Background Music:

Music plays continuously in the background.

Option to mute/unmute the music.

Rematch Option:

After each game, users can choose to play again or exit.

OOP Principles:

Encapsulation: Data hiding through private fields and controlled access using getters and setters.

Inheritance: Shared functionality is reused across game classes.

Abstraction: Complex functionalities like checking win conditions are abstracted into methods.

Polymorphism: Dynamic behavior in handling user and AI moves.

Technologies Used

Java

Java Swing (GUI library)

Java AWT

File Handling for storing user credentials

How to Run

Clone the Repository:

git clone https://github.com/your-username/tic-tac-toe-java.git
cd tic-tac-toe-java

Compile the Code:
Use any Java IDE (like IntelliJ IDEA, Eclipse, or NetBeans) or command-line tools.

javac Main.java

Run the Application:

java Main

Login/Register:

Register as a new user if running the application for the first time.

Log in with valid credentials to proceed to the game.

File Structure

├── LoginForm.java          # Login and Registration functionality
├── Main.java               # Entry point for the application
├── tictactoe.java          # 1 vs 1 game mode
├── tictactoe2.java         # 1 vs Computer game mode
├── user_credentials.txt    # Stores registered user credentials
├── music.wav               # Background music file

Gameplay Instructions

Login or Register:

Enter your email and password to log in.

New users must register by providing their name, email, and password.

Accept terms and conditions to complete registration.

Select Game Mode:

Choose between 1 vs 1 or 1 vs Computer modes.

Play the Game:

Players take turns clicking on the grid to place their mark (X or O).

The first player to align three marks horizontally, vertically, or diagonally wins.

If all cells are filled without a winner, the game ends in a draw.

Rematch or Exit:

After the game, choose to play again or exit.

Background Music:

Use the mute/unmute button to toggle background music.

Future Enhancements

Add difficulty levels for the AI opponent.

Implement online multiplayer functionality.

Store user credentials securely using a database instead of a text file.

Include a leaderboard to track player statistics.
