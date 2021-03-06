package passambler.parser.statement;

import passambler.exception.EngineException;
import passambler.lexer.TokenList;
import passambler.lexer.TokenType;
import passambler.parser.Parser;
import passambler.value.Value;

public class DeferStatement implements Statement {
    @Override
    public boolean canPerform(Parser parser, TokenList tokens) {
        return tokens.current().getType() == TokenType.DEFER;
    }

    @Override
    public Value perform(Parser parser, TokenList tokens) throws EngineException {
        tokens.next();

        parser.getDefers().add(parser.parseExpressionTokens(tokens));

        return null;
    }
}
