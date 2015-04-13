package passambler.pack.std.function;

import passambler.value.function.Function;
import passambler.value.function.FunctionContext;
import passambler.exception.EngineException;
import passambler.exception.ParserException;
import passambler.exception.ParserExceptionType;
import passambler.value.Value;
import passambler.value.BooleanValue;
import passambler.value.ListValue;

public class FunctionFilter extends Value implements Function {
    @Override
    public int getArguments() {
        return 2;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        if (argument == 0) {
            return value instanceof ListValue;
        }

        return value instanceof Function && ((Function) value).getArguments() == 1;
    }

    @Override
    public Value invoke(FunctionContext context) throws EngineException {
        ListValue list = (ListValue) context.getArgument(0);

        Function callback = (Function) context.getArgument(1);
        
        ListValue filteredList = new ListValue();

        for (int i = 0; i < list.getValue().size(); ++i) {
            Value result = callback.invoke(new FunctionContext(context.getParser(), new Value[] { list.getValue().get(i) }));

            if (!(result instanceof BooleanValue)) {
                throw new ParserException(ParserExceptionType.EXPECTED_A_BOOL);
            }

            if (((BooleanValue) result).getValue() == true) {
                filteredList.getValue().add(list.getValue().get(i));
            }
        }

        return filteredList;
    }
}
