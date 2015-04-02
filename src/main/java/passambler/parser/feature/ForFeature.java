package passambler.parser.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import passambler.exception.EngineException;
import passambler.exception.ParserException;
import passambler.exception.ParserExceptionType;
import passambler.lexer.TokenStream;
import passambler.lexer.TokenType;
import passambler.parser.Block;
import passambler.parser.expression.ExpressionParser;
import passambler.parser.Parser;
import passambler.value.Value;
import passambler.value.ValueDict;
import passambler.value.ValueList;
import passambler.value.ValueNum;

public class ForFeature implements Feature {
    @Override
    public boolean canPerform(Parser parser, TokenStream stream) {
        return stream.first().getType() == TokenType.FOR;
    }

    @Override
    public Value perform(Parser parser, TokenStream stream) throws EngineException {
        List<String> arguments = new ArrayList<>();

        stream.next();
        stream.match(TokenType.LPAREN);

        stream.next();

        TokenStream left = new TokenStream(parser.parseExpressionTokens(stream, TokenType.RPAREN, TokenType.COL));
        Value right = null;

        if (stream.current().getType() == TokenType.COL) {
            stream.next();

            right = parser.parseExpression(stream, TokenType.RPAREN);

            stream.match(TokenType.RPAREN);
        }

        stream.next();

        Block callback = parser.parseBlock(stream);

        if (right != null) {
            while (left.hasNext()) {
                left.match(TokenType.IDENTIFIER);

                arguments.add(left.current().getValue());

                if (left.peek() != null) {
                    left.next();

                    left.match(TokenType.COMMA);
                }

                left.next();
            }
        }

        Value value = right == null ? new ExpressionParser(parser, left.copy()).parse() : right;

        if (value instanceof ValueList) {
            ValueList list = (ValueList) value;

            for (int i = 0; i < list.getValue().size(); ++i) {
                if (arguments.size() == 1) {
                    callback.getParser().getScope().setSymbol(arguments.get(0), list.getValue().get(i));
                } else if (arguments.size() == 2) {
                    callback.getParser().getScope().setSymbol(arguments.get(0), new ValueNum(i));
                    callback.getParser().getScope().setSymbol(arguments.get(1), list.getValue().get(i));
                } else if (arguments.size() > 2) {
                    throw new ParserException(ParserExceptionType.INVALID_ARGUMENT_COUNT, stream.first().getPosition(), 2, arguments.size());
                }

                Value result = callback.invoke();

                if (result != null) {
                    return result;
                }
            }
        } else if (value instanceof ValueDict) {
            ValueDict dict = (ValueDict) value;

            for (Map.Entry<Value, Value> entry : dict.getValue().entrySet()) {
                if (arguments.size() == 1) {
                    callback.getParser().getScope().setSymbol(arguments.get(0), entry.getValue());
                } else if (arguments.size() == 2) {
                    callback.getParser().getScope().setSymbol(arguments.get(0), entry.getKey());
                    callback.getParser().getScope().setSymbol(arguments.get(1), entry.getValue());
                } else if (arguments.size() > 2) {
                    throw new ParserException(ParserExceptionType.INVALID_ARGUMENT_COUNT, stream.first().getPosition(), 2, arguments.size());
                }

                Value result = callback.invoke();

                if (result != null) {
                    return result;
                }
            }
        } else {
            throw new ParserException(ParserExceptionType.CANNOT_ITERATE, stream.first().getPosition());
        }

        return null;
    }
}