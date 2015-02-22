package passambler.pkg.file.function;

import java.nio.file.Files;
import java.nio.file.Paths;
import passambler.parser.Parser;
import passambler.parser.ParserException;
import passambler.function.Function;
import passambler.value.Value;
import passambler.value.ValueNum;
import passambler.value.ValueStr;

public class FunctionSize extends Function {
    @Override
    public int getArguments() {
        return 1;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        return value instanceof ValueStr;
    }

    @Override
    public Value invoke(Parser parser, Value... arguments) throws ParserException {
        try {
            return new ValueNum(Files.size(Paths.get(((ValueStr) arguments[0]).getValue())));
        } catch (Exception e) {
            return null;
        }
    }
}