package passambler.parser.feature;

import java.util.List;
import passambler.exception.EngineException;
import passambler.lexer.TokenStream;
import passambler.lexer.TokenType;
import passambler.parser.ArgumentDefinition;
import passambler.parser.Block;
import passambler.parser.Parser;
import passambler.value.Value;
import passambler.value.function.FunctionUser;

public class FunctionFeature implements Feature {
    @Override
    public boolean canPerform(Parser parser, TokenStream stream) {
        return stream.first().getType() == TokenType.FN;
    }

    @Override
    public Value perform(Parser parser, TokenStream stream) throws EngineException {
        stream.next();

        stream.match(TokenType.IDENTIFIER);

        String name = stream.current().getValue();

        stream.next();

        List<ArgumentDefinition> arguments = parser.argumentDefinitions(stream);

        if (stream.peek() == null) {
            parser.getScope().setSymbol(name, new FunctionUser(null, arguments));
        } else {
            stream.next();

            Block callback = parser.block(stream);

            parser.getScope().setSymbol(name, new FunctionUser(callback, arguments));
        }

        return null;
    }
}
