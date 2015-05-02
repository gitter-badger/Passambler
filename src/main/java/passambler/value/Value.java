package passambler.value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import passambler.lexer.Token;
import passambler.exception.ParserException;

public class Value {
    public static final BooleanValue VALUE_TRUE = new BooleanValue(true);
    public static final BooleanValue VALUE_FALSE = new BooleanValue(false);
    public static final Value VALUE_NIL = new Value() {
        @Override
        public String toString() {
            return "nil";
        }
    };

    protected boolean constant = false;

    protected Map<String, Property> properties = new HashMap();

    protected Object value;

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public boolean isConstant() {
        return constant;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Map<String, Property> getProperties() {
        return properties;
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

    public Value onOperator(Value value, Token operatorToken) throws ParserException {
        switch (operatorToken.getType()) {
            case EQUAL:
                return new BooleanValue(equals(value));
            case NEQUAL:
                return new BooleanValue(!equals(value));
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
    public boolean equals(Object object) {
        if (object instanceof Value) {
            return equals((Value) object);
        }

        return super.equals(object);
    }

    @Override
    public String toString() {
        return value == null ? VALUE_NIL.toString() : value.toString();
    }

    public static Value toValue(Object object) {
        if (object instanceof String || object instanceof Date) {
            return new StringValue(String.valueOf(object));
        } else if (object instanceof Long) {
            return new NumberValue((Long) object);
        } else if (object instanceof Double) {
            return new NumberValue((Double) object);
        } else if (object instanceof Integer) {
            return new NumberValue((Integer) object);
        } else if (object instanceof Boolean) {
            return new BooleanValue((Boolean) object);
        }

        return Value.VALUE_NIL;
    }
}
