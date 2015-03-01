package passambler.pkg.std.function;

import java.util.Scanner;
import passambler.function.Function;
import passambler.parser.Parser;
import passambler.parser.ParserException;
import passambler.value.Value;
import passambler.value.ValueBool;
import passambler.value.ValueNum;

public class FunctionReadNum extends Function {
    @Override
    public int getArguments() {
        return -1;
    }

    @Override
    public boolean isArgumentValid(Value value, int argument) {
        return argument == 0 && value instanceof ValueBool;
    }

    @Override
    public Value invoke(Parser parser, Value... arguments) throws ParserException {
        boolean floating = arguments.length == 0 ? false : ((ValueBool) arguments[0]).getValue();

        Scanner scanner = new Scanner(System.in);

        try {
            if (floating) {
                double d = scanner.nextDouble();

                return new ValueNum(d);
            } else {
                int n = scanner.nextInt();

                return new ValueNum(scanner.nextInt());
            }
        } catch (Exception e) {
            return new ValueBool(false);
        }
    }
}
