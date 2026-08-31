# Shell in Java

A simple shell implemented in Java for interactive command execution.

This project is a lightweight terminal-style shell inspired by Unix command line tools. It focuses on reading user input, parsing commands, and executing programs from the system shell.

## Features

- Interactive command prompt
- Builtin commands: `echo`, `type`, `exit`, `pwd`, `cd`
- Directory navigation (absolute, relative, and home `~` paths)
- Runs external commands in the correct working directory
- **Output redirection** — redirect stdout/stderr to files
  - `>` / `1>` — redirect stdout to a file (overwrites)
  - `2>` — redirect stderr to a file (overwrites)
  - `>>` / `1>>` — append stdout to a file
  - `2>>` — append stderr to a file
- Built for Java-based shell experimentation

## Run the project

```bash
javac src/main/java/*.java
java -cp src/main/java Main
```

## Redirection examples

```bash
$ echo hello > output.txt        # writes "hello" to output.txt
$ echo first >> output.txt       # appends "first" to output.txt
$ cat nonexistent 2> errors.txt  # sends error message to errors.txt
$ cat missing 2>> errors.txt     # appends error to errors.txt
```

## Project structure

```text
src/
  main/
    java/
      Main.java         — REPL loop, command parsing, and dispatch
      Navigation.java   — cd/pwd and working directory tracking
      Redirection.java  — parses and applies >, 1>, 2>, >>, 1>>, 2>>
```

## Purpose

This project was built as a learning exercise in understanding how shells work, including command input, execution flow, and terminal behavior.
