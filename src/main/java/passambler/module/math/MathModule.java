package passambler.module.math;

import java.util.Map;
import passambler.module.Module;
import passambler.module.math.function.*;
import passambler.value.Value;
import passambler.value.NumberValue;

public class MathModule implements Module {
    @Override
    public String getId() {
        return "math";
    }

    @Override
    public Module[] getChildren() {
        return null;
    }

    @Override
    public void apply(Map<String, Value> symbols) {
        symbols.put("sin", new SinFunction());
        symbols.put("cos", new CosFunction());
        symbols.put("tan", new TanFunction());
        symbols.put("abs", new AbsFunction());
        symbols.put("ceil", new CeilFunction());
        symbols.put("floor", new FloorFunction());
        symbols.put("sqrt", new SqrtFunction());
        symbols.put("log", new LogFunction());
        symbols.put("log10", new Log10Function());
        symbols.put("rand", new RandFunction());

        symbols.put("PI", new NumberValue(Math.PI));
        symbols.put("E", new NumberValue(Math.E));
    }
}
