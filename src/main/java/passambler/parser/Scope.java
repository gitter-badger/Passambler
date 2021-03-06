package passambler.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import passambler.value.Value;

public class Scope {
    private Scope parent;

    private List<String> visibles = new ArrayList<>();
    private Map<String, Value> symbols = new HashMap();

    public Scope() {
        this(null);
    }

    public Scope(Scope parentScope) {
        parent = parentScope;
    }

    public void setSymbol(String key, Value value) {
        if (parent != null && parent.hasSymbol(key)) {
            parent.setSymbol(key, value);
        } else {
            symbols.put(key, value);
        }
    }

    public Value getSymbol(String key) {
        if (symbols.containsKey(key)) {
            return symbols.get(key);
        } else if (parent != null) {
            return parent.getSymbol(key);
        } else {
            return null;
        }
    }

    public boolean hasSymbol(String key) {
        return getSymbol(key) != null;
    }

    public Map<String, Value> getSymbols() {
        return symbols;
    }

    public void setVisible(String name) {
        visibles.add(name);
    }

    public boolean isVisible(String name) {
        return visibles.contains(name);
    }
}
