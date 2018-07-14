package hr.fer.zemris.java.hw07.shell.commands.massrename;

import hr.fer.zemris.java.hw07.shell.ShellIOException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents parser for {@link NameBuilder}.
 */
public class NameBuilderParser {

    /**
     * Lexer for this parser.
     */
    private NameBuilderLexer lexer;

    /**
     * Composition of all parsed {@link NameBuilder}.
     */
    private NameBuilder compositionNameBuilder;


    /**
     * Basic constructor.
     *
     * @param izraz Izraz that will be parsed
     */
    public NameBuilderParser(String izraz) {
        this.lexer = new NameBuilderLexer(izraz);
        parseBuilder();
    }

    /**
     * This method is used for parsing izraz into compositionNameBuilder.
     */
    private void parseBuilder() {
        List<NameBuilder> builders = new ArrayList<>();

        NameBuilderToken token;

        while (true) {
            token = lexer.nextToken();
            if (token.getType() == NameBuilderTokenType.EOF) {
                break;
            } else if (token.getType() == NameBuilderTokenType.BEGINCOMMAND) {
                lexer.setInsideCommand(true);

                token = lexer.nextToken();
                if (token.getType() != NameBuilderTokenType.NUMBER) {
                    throw new ShellIOException("Invalid command!");
                }
                int groupNumber = Integer.parseInt(token.getValue());
                token = lexer.nextToken();
                if (token.getType() == NameBuilderTokenType.ENDCOMMAND) {
                    lexer.setInsideCommand(false);
                    builders.add(new GroupNameBuilder(groupNumber));
                } else if (token.getType() == NameBuilderTokenType.COMMA) {
                    token = lexer.nextToken();
                    if (token.getType() != NameBuilderTokenType.NUMBER) {
                        throw new ShellIOException("Invalid command!");
                    }

                    builders.add(new GroupNameBuilder(groupNumber, Integer.parseInt(token.getValue()), token.getValue()
                            .charAt(0) == '0'));

                    token = lexer.nextToken();

                    if (token.getType() != NameBuilderTokenType.ENDCOMMAND) {
                        throw new ShellIOException("Invalid command!");
                    }
                    lexer.setInsideCommand(false);
                }
            } else if (token.getType() == NameBuilderTokenType.STRING) {
                builders.add(new StringNameBuilder(token.getValue()));
            }

        }

        compositionNameBuilder = new CompositionNameBuilder(builders);
    }

    /**
     * Getter for name builder.
     *
     * @return Name builder
     */
    public NameBuilder getNameBuilder() {
        return compositionNameBuilder;
    }
}
