package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import javafx.scene.shape.Path;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * This class represents charsets command. It takes no arguments and lists names of all supported charsets.
 * A single charset name is written per line.
 */
public class CharsetCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {


        if (arguments.length() != 0) {
            env.writeln("Charset command must be called without arguments!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        SortedMap<String, Charset> charsets = Charset.availableCharsets();

        for (String s : charsets.keySet()) {
            env.writeln(s);
        }
        env.write(env.getPromptSymbol() + " ");

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "charsets";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Charset command.");
        description.add("It takes no arguments and lists names of all supported charsets.");
        description.add("A single charset name is written per line.");

        return description;
    }
}
