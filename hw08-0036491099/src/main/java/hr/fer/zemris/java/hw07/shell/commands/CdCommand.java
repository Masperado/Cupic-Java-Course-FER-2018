package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents cd command.
 * It is used for changing working directory.
 */
public class CdCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String directoryString = ShellUtility.parsePath(arguments);

        int currentIndexInArgument = directoryString.length();

        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, directoryString.length()).filter(i -> directoryString.charAt(i) == '"' || directoryString.charAt(i) == '\\').count();

        }

        if (currentIndexInArgument != arguments.length()) {
            env.writeln("Invalid number of arguments to cd function!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Path newDirectory = env.getCurrentDirectory().resolve(directoryString).normalize();

        try {
            env.setCurrentDirectory(newDirectory);
            env.writeln("New directory: " + env.getCurrentDirectory());
        } catch (ShellIOException ex) {
            env.writeln("Directory doesn't exist!");
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "cd";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Cd command.");
        description.add("It is used for changing working directory.");

        return description;
    }
}
