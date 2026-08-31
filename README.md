# Shell in Java

A simple shell implemented in Java for interactive command execution.

This project is a lightweight terminal-style shell inspired by Unix command line tools. It focuses on reading user input, parsing commands, and executing programs from the system shell.

## Features

- Interactive command prompt
- Builtin commands: `echo`, `type`, `exit`, `pwd`, `cd`
- Directory navigation (absolute, relative, and home `~` paths)
- Runs external commands in the correct working directory
- Built for Java-based shell experimentation

## Run the project

```bash
javac src/main/java/*.java
java -cp src/main/java Main
```

## Project structure

```text
src/
  main/
    java/
      Main.java
      Navigation.java
```

## Purpose

This project was built as a learning exercise in understanding how shells work, including command input, execution flow, and terminal behavior.
