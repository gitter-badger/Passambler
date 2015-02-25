package passambler.value;

import java.util.HashMap;
import java.util.Map;
import passambler.lexer.Token;

public class Value {
    public static ValueBool VALUE_TRUE = new ValueBool(true);
    public static ValueBool VALUE_FALSE = new ValueBool(false);
    public static ValueNil VALUE_NIL = new ValueNil();

    protected Map<String, Property> properties = new HashMap();

    protected Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Property getProperty(String key) {
        return properties.get(key);
    }

    public boolean hasProperty(String key) {
        return getProperty(key) != null;
    }

    public void setProperty(String key, Value value) {
        properties.put(key, new Property(value));
    }

    public void setProperty(String key, Property property) {
        properties.put(key, property);
    }

    public Value onOperator(Value value, Token.Type tokenType) {
        switch (tokenType) {
            case EQUAL:
                return new ValueBool(equals(value));
            case NEQUAL:
                return new ValueBool(!equals(value));
            case ASSIGN:
                return value;
        }

        return null;
    }
    
    public boolean equals(Value value) {
        if (getValue() == null && value.getValue() == null) {
            return true;
        }
        
        if ((getValue() != null && value.getValue() == null) || (getValue() == null && value.getValue() != null)) {
            return false;
        }
        
        return getValue().equals(value.getValue());
    }

    @Override
    public String toString() {
        return value == null ? VALUE_NIL.toString() : value.toString();
    }
}
