package passambler.value;

import java.math.BigDecimal;
import passambler.lexer.Token;

public class ValueNum extends Value {
    public ValueNum(double data) {
        this(new BigDecimal(data));
    }
    
    public ValueNum(int data) {
        this(new BigDecimal(data));
    }

    public ValueNum(BigDecimal data) {
        setValue(data);
    }

    @Override
    public BigDecimal getValue() {
        return (BigDecimal) value;
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
                    return new ValueNum(getValue().add(((ValueNum) value).getValue()));
                case MINUS:
                case ASSIGN_MINUS:
                    return new ValueNum(getValue().subtract(((ValueNum) value).getValue()));
                case MULTIPLY:
                case ASSIGN_MULTIPLY:
                    return new ValueNum(getValue().multiply(((ValueNum) value).getValue()));
                case DIVIDE:
                case ASSIGN_DIVIDE:
                    return new ValueNum(getValue().divide(((ValueNum) value).getValue()));
                case POWER:
                case ASSIGN_POWER:
                    return new ValueNum(getValue().pow(((ValueNum) value).getValueAsInteger()));
                case MODULO:
                case ASSIGN_MODULO:
                    return new ValueNum(getValue().remainder(((ValueNum) value).getValue()));
                case GT:
                    return new ValueBool(getValue().compareTo(((ValueNum) value).getValue()) > 0);
                case LT:
                    return new ValueBool(getValue().compareTo(((ValueNum) value).getValue()) < 0);
                case GTE:
                    return new ValueBool(getValue().compareTo(((ValueNum) value).getValue()) >= 0);
                case LTE:
                    return new ValueBool(getValue().compareTo(((ValueNum) value).getValue()) <= 0);
                case RANGE:
                    ValueList list = new ValueList();
                    
                    for (int i = getValueAsInteger(); i <= ((ValueNum) value).getValueAsInteger(); ++i) {
                        list.add(new ValueNum(i));
                    }
                
                    return list;
            }
        }

        return super.onOperator(value, tokenType);
    }

    @Override
    public String toString() {
        return getValue().toPlainString();
    }
}
