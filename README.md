# Poker Simulator

## Overview

Poker Simulator is a comprehensive Java application designed to simulate and analyze poker games. Using a combination of multi-threading and Monte Carlo analysis (https://en.wikipedia.org/wiki/Monte_Carlo_method), the software aims to provide both the statistical probabilities of having a type of hand among any given 5-card hand (https://en.wikipedia.org/wiki/Poker_probability), and generate a table containing the heads up odds for any two sets of holecards (https://caniwin.com/texasholdem/preflop/heads-up.php). Developed with the principles of Object-Oriented Programming (OOP), and Test-Driven Development (TDD) this software is optimized for performance, scalability, and ease of use.

## Features

- **Multi-threaded**: Leverages multiple cores to more quickly execute embarrasingly parallel (https://en.wikipedia.org/wiki/Embarrassingly_parallel) tasks.
- **Statistical Analysis**: Provides detailed stats related to hand probabilities and game outcomes.
- **Flexible Execution**: Command-line options to adjust the number of threads and iterations.

## Software Design Patterns and Paradigms

- **Object-Oriented Programming (OOP)**: Uses encapsulation, inheritance, and polymorphism for cleaner, reusable code.
- **Single Responsibility Principle**: Each class is focused on a specific aspect of the simulation for easier maintenance and debugging.
  
## Prerequisites

- Java 1.8 or higher
- Maven

## Getting Started

### Installation

1. **Clone the Repository**
    ```shell
    git clone https://github.com/andrewalia/poker-simulator.git
    ```

2. **Navigate to Project Directory**
    ```shell
    cd poker-simulator
    ```

3. **Build the Project with Maven**
    ```shell
    mvn clean install
    ```

4. **Run the Application**
    ```shell
    java -jar target/poker-0.01-jar-with-dependencies.jar
    ```

## Usage

- **Launch the application**: Execute the generated JAR in the `target` directory.
- **Command-Line Options**: Use flags to specify the number of threads and iterations.
  ```shell
  java -jar target/poker-0.01-jar-with-dependencies.jar -t4 -i10000
  ```

## Contributing

Contributions, issues, and feature requests are welcome. Please feel free to fork the repository and create pull requests for your contributions.

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details.
