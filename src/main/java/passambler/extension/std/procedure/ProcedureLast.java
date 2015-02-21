package passambler.extension.std.procedure;

import passambler.parser.Parser;
import passambler.parser.ParserException;
import passambler.procedure.Procedure;
import passambler.value.IndexedValue;
import passambler.value.Value;
import passambler.value.ValueNum;

public class ProcedureLast implements Procedure {
    @Override
    public int getArguments() {
        return 1;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        return value instanceof IndexedValue;
    }

    @Override
    public Value invoke(Parser parser, Value... arguments) throws ParserException {
        IndexedValue value = (IndexedValue) arguments[0];
        
        return value.getIndex(new ValueNum(value.getIndexCount() - 1));
    }
}