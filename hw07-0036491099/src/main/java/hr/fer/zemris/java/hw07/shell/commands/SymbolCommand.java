package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents symbol command. If used with only symbol name it print symbol of MyShell for given symbol name.
 * If used with two arguments, it sets symbol of given name to given character.
 */
public class SymbolCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] elements = arguments.split(" ");

        if (elements.length == 1) {
            Character symbol;
            switch (elements[0]) {
                case "PROMPT":
                    symbol = env.getPromptSymbol();
                    break;
                case "MORELINES":
                    symbol = env.getMorelinesSymbol();
                    break;
                case "MULTILINE":
                    symbol = env.getMultilineSymbol();
                    break;
                default:
                    env.writeln("Invalid symbol name!");
                    env.write(env.getPromptSymbol() + " ");
                    return ShellStatus.CONTINUE;
            }

            env.writeln("Symbol for " + elements[0] + " is '" + symbol + "'");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        if (elements.length != 2 || elements[1].length() != 1) {
            env.writeln("Invalid arguments for symbol command.");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Character symbol = elements[1].charAt(0);

        switch (elements[0]) {
            case "PROMPT":
                env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + symbol + "'");
                env.setPromptSymbol(symbol);
                break;
            case "MORELINES":
                env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + symbol + "'");
                env.setMorelinesSymbol(symbol);
                break;
            case "MULTILINE":
                env.writeln("Symbol for PROMPT changed from '" + env.getMultilineSymbol() + "' to '" + symbol + "'");
                env.setMultilineSymbol(symbol);
                break;
            default:
                env.writeln("Invalid symbol name!");
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;

    }

    @Override
    public String getCommandName() {
        return "symbol";
    }

    @Override
    public List<String> getCommandDescription() {

        List<String> description = new ArrayList<>();

        description.add("Symbol command.");
        description.add("If used with only symbol name it print symbol of MyShell for given symbol name.");
        description.add("If used with two arguments, it sets symbol of given name to given character.");

        return description;
    }
}
