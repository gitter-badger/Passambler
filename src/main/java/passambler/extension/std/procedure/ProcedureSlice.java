package passambler.extension.std.procedure;

import passambler.parser.Parser;
import passambler.parser.ParserException;
import passambler.procedure.Procedure;
import passambler.value.Value;
import passambler.value.ValueList;
import passambler.value.ValueNum;

public class ProcedureSlice implements Procedure {
    @Override
    public int getArguments() {
        return 3;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        if (argument == 0) {
            return value instanceof ValueList;
        }

        return value instanceof ValueNum;
    }

    @Override
    public Value invoke(Parser parser, Value... arguments) throws ParserException {
        ValueList list = (ValueList) arguments[0];

        ValueList subList = new ValueList();

        for (int i = ((ValueNum) arguments[1]).getValue().intValue(); i <= ((ValueNum) arguments[2]).getValue().intValue(); ++i) {
            subList.add(list.get(i));
        }

        return subList;
    }
}
