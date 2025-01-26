# StudyBuddy

StudyBuddy is a Java-based productivity application designed to help users maintain focus by restricting access to selected applications during specified timeframes. It offers persistent settings through save files and includes mechanisms to prevent unauthorized termination.

## Features

- **Application Blocking**: Specify a list of applications to be blocked during designated study periods.
- **Customizable Timeframes**: Set specific time intervals during which the selected applications remain inaccessible.
- **Persistent Configuration**: User settings and preferences are saved to a file, ensuring consistency across sessions.
- **Self-Protection**: Implements measures to prevent the application from being easily closed or terminated, ensuring uninterrupted focus.

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/DevGeorge/StudyBuddy.git
   cd StudyBuddy
   ```
2. **Build the Application**:
   ```bash
   javac -d bin src/*.java
   ```
3. **Run the Application**:
   ```bash
   java -cp bin com.studybuddy.Main
   ```

## Usage

1. **Launch StudyBuddy**.
2. **Configure Blocked Applications**:
   - Navigate to the settings menu.
   - Add the applications you wish to block during study sessions.
3. **Set Study Timeframes**:
   - Define the start and end times for your study periods.
4. **Activate**:
   - Start the session to enable application blocking.

During active sessions, the specified applications will be inaccessible, and StudyBuddy will resist termination attempts to ensure continuous focus.

## Persistence

StudyBuddy saves user configurations to a file located at `~/.studybuddy/PROCESS_LIST.txt`. This file stores the list of blocked applications and defined timeframes, allowing the application to retain settings between sessions.

## Self-Protection Mechanism

To prevent unauthorized closure, StudyBuddy employs system-level hooks and monitors common termination signals. While it strengthens resistance against accidental or intentional termination, users with sufficient system privileges may still forcefully close the application.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your enhancements or bug fixes.
