package passambler.pack.std.function;

import passambler.exception.EngineException;
import passambler.value.function.Function;
import passambler.value.function.FunctionContext;
import passambler.value.Value;
import passambler.value.ValueList;

public class FunctionReverse extends Value implements Function {
    @Override
    public int getArguments() {
        return 1;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        return value instanceof ValueList;
    }

    @Override
    public Value invoke(FunctionContext context) throws EngineException {
        ValueList value = (ValueList) context.getArgument(0);

        ValueList subList = new ValueList();

        for (int i = value.getValue().size() - 1; i >= 0; --i) {
            subList.getValue().add(value.getValue().get(i));
        }

        return subList;
    }
}
