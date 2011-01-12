package org.calculator;

public class OperatorToken extends Token {
    public OperatorToken(String value) {
        super(value);
    }
    
    public OperandToken performOperation(OperandToken operand1, OperandToken operand2) {
        Double result = null;
        
        switch (value.charAt(0)) {
        case '*':
            result = operand1.getDoubleValue() * operand2.getDoubleValue();
            break;
        case '/':
            result = operand1.getDoubleValue() / operand2.getDoubleValue();
            break;
        case '+':
            result = operand1.getDoubleValue() + operand2.getDoubleValue();
            break;
        case '-':
            result = operand1.getDoubleValue() - operand2.getDoubleValue();
            break;
        }
        
        return new OperandToken(String.valueOf(result));
    }
}
