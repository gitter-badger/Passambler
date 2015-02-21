package passambler.extension.std.procedure;

import passambler.parser.Parser;
import passambler.parser.ParserException;
import passambler.procedure.Procedure;
import passambler.value.Value;
import passambler.value.ValueList;

public class ProcedurePop implements Procedure {
    @Override
    public int getArguments() {
        return 1;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        return value instanceof ValueList;
    }

    @Override
    public Value invoke(Parser parser, Value... arguments) throws ParserException {
        ValueList value = (ValueList) arguments[0];
        
        Value last = value.get(value.getIndexCount() - 1);
        
        value.remove(value.getIndexCount() - 1);
        
        return last;
    }
}