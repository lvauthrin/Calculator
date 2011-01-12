package org.calculator;

public class OperandToken extends Token {
    public OperandToken(String value) {
        super(value);
    }
    
    public Double getDoubleValue() {
        return Double.parseDouble(this.value);
    }
}