package passambler.module.std.function;

import passambler.value.function.Function;
import passambler.value.function.FunctionContext;
import passambler.lexer.Lexer;
import passambler.exception.EngineException;
import passambler.exception.ErrorException;
import passambler.value.Value;
import passambler.value.StringValue;

public class EvalFunction extends Value implements Function {
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
        try {
            return context.getParser().parse(new Lexer(((StringValue) context.getArgument(0)).toString()).tokenize());
        } catch (EngineException e) {
            throw new ErrorException(e);
        }
    }
}
