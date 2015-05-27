package passambler.parser.feature;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import passambler.exception.EngineException;
import passambler.exception.ParserException;
import passambler.exception.ParserExceptionType;
import passambler.lexer.TokenList;
import passambler.lexer.TokenType;
import passambler.module.FilesystemModule;
import passambler.module.Module;
import passambler.parser.Parser;
import passambler.value.Property;
import passambler.value.StringValue;
import passambler.value.Value;

public class ImportFeature implements Feature {
    @Override
    public boolean canPerform(Parser parser, TokenList tokens) {
        return tokens.current().getType() == TokenType.IMPORT;
    }

    @Override
    public Value perform(Parser parser, TokenList tokens) throws EngineException {
        tokens.next();

        Value value = parser.parseExpression(tokens, TokenType.AS);

        String alias = null;

        if (tokens.current() != null && tokens.current().getType() == TokenType.AS) {
            tokens.next();
            tokens.match(TokenType.IDENTIFIER);

            alias = tokens.current().getValue();
        }

        if (value instanceof StringValue) {
            String data = value.toString();

            Module module = null;
            String moduleName = null;

            for (String element : data.split("/")) {
                if (module == null) {
                    Module builtInModule = parser.getModules().stream().filter(m -> m.getId().equals(element)).findFirst().orElse(null);

                    if (builtInModule == null) {
                        module = new FilesystemModule(Paths.get(element + ".psm"));
                    } else {
                        module = builtInModule;
                    }
                } else {
                    module = Arrays.asList(module.getChildren()).stream().filter(m -> m.getId().equals(element)).findFirst().get();
                }

                moduleName = element;
            }

            Map<String, Value> symbols = new HashMap();

            module.apply(symbols);

            Value moduleValue = new Value();

            symbols.entrySet().stream().forEach((symbol) -> moduleValue.setProperty(symbol.getKey(), new Property(symbol.getValue())));

            parser.getScope().setSymbol(alias != null ? alias : moduleName, moduleValue);
        } else {
            throw new ParserException(ParserExceptionType.NOT_A_STRING, tokens.current().getPosition());
        }

        return null;
    }
}
