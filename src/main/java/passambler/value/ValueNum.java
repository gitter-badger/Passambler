package passambler.value;

import passambler.function.FunctionSimple;
import passambler.lexer.Token;

public class ValueNum extends Value {
    public ValueNum(int data) {
        this((double) data);
    }

    public ValueNum(Double data) {
        setValue(data);
        
        setProperty("abs", new FunctionSimple() {
            @Override
            public Value getValue() {
                return new ValueNum(Math.abs(ValueNum.this.getValue()));
            }
        });
        
        setProperty("floor", new FunctionSimple() {
            @Override
            public Value getValue() {
                return new ValueNum(Math.floor(ValueNum.this.getValue()));
            }
        });
        
        setProperty("ceil", new FunctionSimple() {
            @Override
            public Value getValue() {
                return new ValueNum(Math.ceil(ValueNum.this.getValue()));
            }
        });
    }

    @Override
    public Double getValue() {
        return (Double) ((double) value);
    }

    public int getValueAsInteger() {
        return getValue().intValue();
    }

    @Override
    public Value onOperator(Value value, Token.Type tokenType) {
        if (value instanceof ValueNum) {
            switch (tokenType) {
                case PLUS:
                case ASSIGN_PLUS:
                    return new ValueNum(getValue() + ((ValueNum) value).getValue());
                case MINUS:
                case ASSIGN_MINUS:
                    return new ValueNum(getValue() - Math.abs(((ValueNum) value).getValue()));
                case MULTIPLY:
                case ASSIGN_MULTIPLY:
                    return new ValueNum(getValue() * ((ValueNum) value).getValue());
                case DIVIDE:
                case ASSIGN_DIVIDE:
                    return new ValueNum(getValue() / ((ValueNum) value).getValue());
                case POWER:
                case ASSIGN_POWER:
                    return new ValueNum(Math.pow(getValue(), ((ValueNum) value).getValue()));
                case MODULO:
                case ASSIGN_MODULO:
                    return new ValueNum(getValue() % ((ValueNum) value).getValue());
                case GT:
                    return new ValueBool(getValue() > ((ValueNum) value).getValue());
                case LT:
                    return new ValueBool(getValue() < ((ValueNum) value).getValue());
                case GTE:
                    return new ValueBool(getValue() >= ((ValueNum) value).getValue());
                case LTE:
                    return new ValueBool(getValue() <= ((ValueNum) value).getValue());
            }
        }

        return super.onOperator(value, tokenType);
    }

    @Override
    public String toString() {
        int i = (int) ((double) getValue());

        return getValue() == i ? String.valueOf(i) : String.valueOf(getValue());
    }
}
