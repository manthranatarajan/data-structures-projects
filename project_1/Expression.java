//MXN220038

//package dsa

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * Class to represent an expression and its evaluation using an expression tree.
 * It supports conversion between infix and postfix expressions and evaluates them.
 * Internal nodes of the tree represent operators, and leaf nodes represent operands.
 * Supported operators: +, *, -, /, %, ^.
 * Parentheses () are used for grouping operations.
 * Operands are represented by long integers.
 *
 * @author Manthra Natarajan
 */
public class Expression {

    /**
     * Enum to represent the types of tokens in the expression.
     * Operators include PLUS, TIMES, MINUS, DIV, MOD, POWER,
     * parentheses are represented by OPEN and CLOSE,
     * NUMBER represents operands, and NIL is a special token.
     */
    public enum TokenType {
        PLUS,
        TIMES,
        MINUS,
        DIV,
        MOD,
        POWER,
        OPEN,
        CLOSE,
        NIL,
        NUMBER,
    }

    /**
     * Class that represents a token in an expression. A token can be an operator
     * or an operand(number).
     */
    public static class Token {

        TokenType token;
        int priority;
        Long number; 
        String string;

        Token(TokenType op, int pri, String tok) {
            token = op;
            priority = pri;
            number = null;
            string = tok;
        }

        //Constructor for number. To be called when other options have been exhausted.
        
        Token(String tok) {
            token = TokenType.NUMBER;
            number = Long.parseLong(tok);
            string = tok;
        }

        boolean isOperand() {
            return token == TokenType.NUMBER;
        }

        public long getValue() {
            return isOperand() ? number : 0;
        }

        public String toString() {
            return string;
        }
    }

    Token element;
    Expression left, right;


    
/**
 
* Converts a given string into a corresponding token (operator, parenthesis, or number).
* This method parses a string representing one token, and determines whether it is an operator,
* a parenthesis, or a number. If it is a number, the token stores the numeric value.
* Operators have precedence levels assigned to them to assist in parsing expressions.
 */

    static Token getToken(String tok) {
        Token result;
        switch (tok) {
            case "+":
                result = new Token(TokenType.PLUS, 1, tok);
                break;
            case "*":
                result = new Token(TokenType.TIMES, 2, tok);
                break;
            case "-":
                result = new Token(TokenType.MINUS, 1, tok);
                break;
            case "/":
                result = new Token(TokenType.DIV, 2, tok);
                break;
            case "%":
                result = new Token(TokenType.MOD, 2, tok);
                break;
            case "^":
                result = new Token(TokenType.POWER, 3, tok);
                break;
            case "(":
                result = new Token(TokenType.OPEN, 0, tok);
                break;
            case ")":
                result = new Token(TokenType.CLOSE, 0, tok);
                break;
            default:
                result = new Token(tok);
                break;
        }
        return result;
    }


    private Expression() {
        element = null;
    }

    private Expression(Token oper, Expression left, Expression right) {
        this.element = oper;
        this.left = left;
        this.right = right;
    }

    private Expression(Token num) {
        this.element = num;
        this.left = null;
        this.right = null;
    }


/**
 
* Converts a given list of tokens representing an infix expression into an expression tree.
* The method uses the Shunting Yard algorithm to parse the infix expression and construct
* the corresponding binary expression tree. Operands are stored in the output queue, while
* operators and parentheses are handled using a stack.
*/
    public static Expression infixToExpression(List<Token> exp) {
        Deque<Expression> stack = new ArrayDeque<>();
        Deque<Token> operator = new ArrayDeque<>();

        for (Token token : exp) {
            if (token.isOperand()) {
                stack.push(new Expression(token));
            } else if (token.token == TokenType.OPEN) {
                operator.push(token);
            } else if (token.token == TokenType.CLOSE) {
                while (!operator.isEmpty() && operator.peek().token != TokenType.OPEN) {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    stack.push(new Expression(operator.pop(), left, right));
                }
                operator.pop();
            } else {
                while (!operator.isEmpty() && operator.peek().priority >= token.priority) {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    stack.push(new Expression(operator.pop(), left, right));
                }
                operator.push(token);
            }
        }

        while (!operator.isEmpty()) {
            Expression right = stack.pop();
            Expression left = stack.pop();
            stack.push(new Expression(operator.pop(), left, right));
        }

        return stack.pop();
    }


/**
 
* Converts a given infix expression (as a list of tokens) into a postfix expression.
* The method uses the Shunting Yard algorithm to handle operator order, and
* proper placement of operands and operators in the postfix notation.
*/
    public static List<Token> infixToPostfix(List<Token> exp) {
        List<Token> result = new LinkedList<>();
        Queue<Token> output = new LinkedList<>();
        Deque<Token> stack = new ArrayDeque<>();

        for (Token token : exp) {
            if (token.isOperand()) {
                output.add(token);
            } else if (token.token == TokenType.OPEN) {
                stack.push(token);
            } else if (token.token == TokenType.CLOSE) {
                while (!stack.isEmpty() && stack.peek().token != TokenType.OPEN) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && stack.peek().priority >= token.priority) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        while (!output.isEmpty()) {
            result.add(output.remove());
        }

        return result;
    }


/**
* Given a postfix expression as a list of tokens, evaluate it and return its value.
* The expression is evaluated using a stack, with operands being pushed to the stack
* and operators performing operations on the operands. The final result is the value
* remaining in the stack.
*/
    public static long evaluatePostfix(List<Token> exp) {
        Deque<Long> stack = new ArrayDeque<>();
        for (Token token : exp) {
            if (token.isOperand()) {
                stack.push(token.getValue());
            } else {
                long right = stack.pop();
                long left = stack.pop();
                switch (token.token) {
                    case PLUS:
                        stack.push(left + right);
                        break;
                    case TIMES:
                        stack.push(left * right);
                        break;
                    case MINUS:
                        stack.push(left - right);
                        break;
                    case DIV:
                        stack.push(left / right);
                        break;
                    case MOD:
                        stack.push(left % right);
                        break;
                    case POWER:
                        stack.push((long) Math.pow(left, right));
                        break;
                }
            }
        }
        return stack.pop();
    }


/**
 
* Evaluates an arithmetic expression represented by an expression tree and returns its value.
* The method recursively evaluates the left and right subtrees and performs the corresponding
* operation at each internal node.
*/
    public static long evaluateExpression(Expression tree) {
        if (tree.left == null && tree.right == null) {
            return tree.element.getValue();
        }

        long left = evaluateExpression(tree.left);
        long right = evaluateExpression(tree.right);
        
        switch (tree.element.token) {
            case PLUS:
                return left + right;
            case TIMES:
                return left * right;
            case MINUS:
                return left - right;
            case DIV:
                return left / right;
            case MOD:
                return left % right;
            case POWER:
                return (long) Math.pow(left, right);
            default:
                return 0;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        int count = 0;
        while (in.hasNext()) {
            String s = in.nextLine();
            List<Token> infix = new LinkedList<>();
            Scanner sscan = new Scanner(s);
            int len = 0;
            while (sscan.hasNext()) {
                infix.add(getToken(sscan.next()));
                len++;
            }
            if (len > 0) {
                count++;
                System.out.println("Expression number: " + count);
                System.out.println("Infix expression: " + infix);
                Expression exp = infixToExpression(infix);
                List<Token> post = infixToPostfix(infix);
                System.out.println("Postfix expression: " + post);
                long pval = evaluatePostfix(post);
                long eval = evaluateExpression(exp);
                System.out.println("Postfix eval: " + pval + " Exp eval: " + eval + "\n");
            }
        }
    }
}
