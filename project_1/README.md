# Expression Tree Processor

This Java program provides functionality for parsing, evaluating, and converting arithmetic expressions using expression trees. It supports operations on **infix** (e.g., `2 + 3 * 4`) and **postfix** (e.g., `2 3 4 * +`) representations of arithmetic expressions. Here's an overview of the features:

## Features

1. **Supported Operators**:
   - Addition (`+`), Subtraction (`-`), Multiplication (`*`), Division (`/`), Modulus (`%`), and Exponentiation (`^`).
   - Parentheses (`(`, `)`) for grouping operations.

2. **Tokenization**:
   - Converts strings into tokens (operators, operands, or parentheses) using the `getToken` method.

3. **Infix to Postfix Conversion**:
   - Converts an infix expression to its postfix equivalent using the Shunting Yard algorithm (`infixToPostfix`).

4. **Infix to Expression Tree**:
   - Constructs a binary expression tree from an infix expression (`infixToExpression`).

5. **Expression Evaluation**:
   - Evaluates a postfix expression (`evaluatePostfix`).
   - Evaluates an expression tree (`evaluateExpression`).

6. **Interactive Input**:
   - Reads expressions from a file or standard input.
   - Outputs the results including infix expression, postfix expression, and their evaluations.

## How It Works

1. **Input Handling**:
   - Accepts input via a file (specified as a command-line argument) or through standard input.
   - Parses each line of input as an arithmetic expression.

2. **Expression Parsing**:
   - Each expression is tokenized into a list of `Token` objects.
   - Converts the tokens into an expression tree (`infixToExpression`) or a postfix list (`infixToPostfix`).

3. **Expression Evaluation**:
   - Computes the value of the expression using either the postfix notation or the constructed expression tree.

4. **Output**:
   - Displays:
     - The original infix expression.
     - The equivalent postfix expression.
     - The evaluated values from both postfix and tree evaluations.

5. **How to Run**

1. Compile the program:
   ```text
   javac Expression.java
3. Execute with input file:
   ```text
   java Expression input.txt
   Or interactively:
   java Expression
