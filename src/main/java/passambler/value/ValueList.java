package passambler.value;

import java.util.ArrayList;
import java.util.List;
import passambler.procedure.Procedure;
import passambler.parser.Parser;
import passambler.parser.ParserException;

public class ValueList extends Value implements IndexedValue {
    protected List<Value> list = new ArrayList<>();

    @Override
    public Value getIndex(Value key) {
        return list.get(((ValueNum) key).getValueAsInteger());
    }

    @Override
    public void setIndex(Value key, Value value) {
        list.set(((ValueNum) key).getValueAsInteger(), value);
    }

    @Override
    public int getIndexCount() {
        return list.size();
    }

    public void add(Value value) {
        list.add(value);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
