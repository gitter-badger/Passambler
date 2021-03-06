package passambler.value;

import java.util.HashMap;
import java.util.Map;
import passambler.lexer.Token;
import passambler.exception.ParserException;
import passambler.util.ValueConstants;
import passambler.util.ValueUtils;

public class Value {
    protected Map<String, Property> properties = new HashMap();

    protected Object value = ValueConstants.NIL;

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

    @Override
    public boolean equals(Object object) {
        if (object instanceof Value) {
            return ValueUtils.compare(this, (Value) object);
        }

        return super.equals(object);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
