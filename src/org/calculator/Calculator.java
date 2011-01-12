package org.calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


/**
 * Calculator class - implemented as a two pass list traversal instead of a tree
 * since only a few operations are supported.
 */
public class Calculator {

    // Sets that contain valid operators (primary -> * and /) and (secondary -> + and -)
    private static Set<Character> validOperators = new HashSet<Character>();
    private static Set<Character> primaryOperators = new HashSet<Character>();
    private static Set<Character> secondaryOperators = new HashSet<Character>();
    
    static {
        primaryOperators.add(new Character('*'));
        primaryOperators.add(new Character('/'));
        secondaryOperators.add(new Character('+'));
        secondaryOperators.add(new Character('-'));
        validOperators.addAll(primaryOperators);
        validOperators.addAll(secondaryOperators);
    }
    
    public Number calculate(List<Token> tokens) throws Exception {
        // Make two passes for each operator precedence
        performOperations(primaryOperators, tokens);
        performOperations(secondaryOperators, tokens);        
        // The final value is the result
        return ((OperandToken) tokens.get(0)).getDoubleValue();
    }
    
    /**
     * Go through the token list and perform calculations
     */
    public void performOperations(Set<Character> operators, List<Token> tokens) {
        for (int i = 1; i < tokens.size() - 1; i += 2) {
            OperandToken operand1 = (OperandToken) tokens.get(i-1);
            OperatorToken operator = (OperatorToken) tokens.get(i);
            OperandToken operand2 = (OperandToken) tokens.get(i+1);

            if (operators.contains(operator.value.charAt(0))) {
                tokens.set(i - 1, operator.performOperation(operand1, operand2));
                tokens.remove(i);
                tokens.remove(i);
                i -=2;
            }
        }        
    }

    /**
     * Create tokens from a string equation
     */
    public List<Token> createTokens(String equation) throws Exception {
        if (equation == null || equation.trim().isEmpty()) {
            throw new Exception("Equation cannot be empty or null.");
        }
        
        List<Token> tokens = new ArrayList<Token>();        
        Scanner scanner = new Scanner(equation);

        scanner.useDelimiter("\\p{javaWhitespace}*");
        
        if (!scanner.hasNextDouble()) {
            throw new Exception("Invalid equation.");
        }
        
        tokens.add(new OperandToken(String.valueOf(scanner.nextDouble())));
        
        while (scanner.hasNext()) {
            if (!scanner.hasNext("[\\+\\-\\*/]")) {
                throw new Exception("Invalid equation.");
            }
            
            tokens.add(new OperatorToken(scanner.next("[\\+\\-\\*/]")));
                        
            if (!scanner.hasNextDouble()) {
                throw new Exception("Invalid equation.");
            }
            
            tokens.add(new OperandToken(String.valueOf(scanner.nextDouble())));            
        }

        return tokens;
    }
   
    public static void main(String[] args) throws Exception {
        
        Calculator calculator = new Calculator();

        // Test code
//        String[] equations = new String[] {
//                null,
//                "",
//                "nonsense",
//                "+",
//                "1 1",
//                "1 +",
//                "+ +",
//                // legal
//                "1",
//                "1 + 1",
//                "1 + 2 * 3 - 4 / 5",
//                "1 + 2 * 3 / 4 - 5"
//        };
//
//        for (String equation : equations) {
//            try {
//                List<Token> tokens = calculator.createTokens(equation);
//                System.out.println(equation + " = " + calculator.calculate(tokens));
//            }
//            catch (Exception e) {
//                System.err.println("Error evaluating equation: " + e);
//            }
//        }

        String input = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (!(input = in.readLine()).equals("quit")) {
            try {
                List<Token> tokens = calculator.createTokens(input);
                System.out.println(input + " = " + calculator.calculate(tokens));
            }
            catch (Exception e) {
                System.err.println("Error evaluating equation: " + e);
            }
            
        }
    }
}
