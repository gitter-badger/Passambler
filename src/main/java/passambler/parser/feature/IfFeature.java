package passambler.parser.feature;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import passambler.exception.EngineException;
import passambler.exception.ParserException;
import passambler.exception.ParserExceptionType;
import passambler.lexer.Token;
import passambler.lexer.TokenStream;
import passambler.lexer.TokenType;
import passambler.parser.Block;
import passambler.parser.expression.ExpressionParser;
import passambler.parser.Parser;
import passambler.value.Value;
import passambler.value.BooleanValue;

public class IfFeature implements Feature {
    @Override
    public boolean canPerform(Parser parser, TokenStream stream) {
        return stream.first().getType() == TokenType.IF;
    }

    @Override
    public Value perform(Parser parser, TokenStream stream) throws EngineException {
        stream.next();

        boolean elseCondition = false;

        Map<BooleanValue, Block> cases = new LinkedHashMap();

        while (stream.hasNext()) {
            if (!elseCondition) {
                stream.match(TokenType.LEFT_PAREN);
                stream.next();

                List<Token> tokens = parser.parseExpressionTokens(stream, TokenType.RIGHT_PAREN);

                stream.match(TokenType.RIGHT_PAREN);

                Value condition = new ExpressionParser(parser, new TokenStream(tokens)).parse();

                if (!(condition instanceof BooleanValue)) {
                    throw new ParserException(ParserExceptionType.EXPECTED_A_BOOL, tokens.get(0).getPosition());
                }

                stream.next();

                cases.put((BooleanValue) condition, parser.parseBlock(stream));

                tokens.clear();
            } else {
                cases.put(new BooleanValue(true), parser.parseBlock(stream));
            }

            stream.next();

            if (stream.current() != null) {
                if (elseCondition) {
                    throw new ParserException(ParserExceptionType.BAD_SYNTAX, stream.first().getPosition(), "else should be the last statement");
                }

                stream.match(TokenType.ELSE, TokenType.ELSEIF);

                if (stream.current().getType() == TokenType.ELSE) {
                    elseCondition = true;
                }

                stream.next();
            }
        }

        for (Map.Entry<BooleanValue, Block> entry : cases.entrySet()) {
            if (entry.getKey().getValue() == true) {
                Value result = entry.getValue().invoke();

                if (result != null) {
                    return result;
                }

                break;
            }
        }

        return null;
    }
}