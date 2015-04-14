package passambler.pack.file.function;

import java.nio.file.Path;
import java.nio.file.Paths;
import passambler.value.function.Function;
import passambler.value.function.FunctionContext;
import passambler.exception.EngineException;
import passambler.value.Value;
import passambler.value.StringValue;

public abstract class SimpleFileFunction extends Value implements Function {
    @Override
    public int getArguments() {
        return 1;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        return value instanceof StringValue;
    }

    @Override
    public Value invoke(FunctionContext context) throws EngineException {
        return getReturnValue(Paths.get(((StringValue) context.getArgument(0)).getValue()));
    }

    public abstract Value getReturnValue(Path file) throws EngineException;
}