package passambler.procedure;

import passambler.parser.Parser;
import passambler.parser.ParserException;
import passambler.value.Value;

public abstract class ProcedureSimple implements Procedure {
    @Override
    public int getArguments() {
        return 0;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        return false;
    }

    @Override
    public Value invoke(Parser parser, Value... arguments) throws ParserException {
        return getValue();
    }
    
    public abstract Value getValue();
}