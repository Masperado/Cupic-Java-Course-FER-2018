package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents mkdir command. It takes a single argument which has to be directory name.
 * It creates the appropriate directory structure.
 */
public class MkdirCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String directoryString = ShellUtility.parsePath(arguments);

        int currentIndexInArgument = directoryString.length();

        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, directoryString.length()).filter(i -> directoryString.charAt(i) == '"' || directoryString.charAt(i) == '\\').count();

        }

        if (currentIndexInArgument != arguments.length()) {
            env.writeln("Invalid number of arguments to mkdir function!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.createDirectories(Paths.get(directoryString));
            env.writeln("Directory created");
        } catch (IOException e) {
            env.writeln("Error while creating directory.");
        }


        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;

    }

    @Override
    public String getCommandName() {
        return "mkdir";
    }

    @Override
    public List<String> getCommandDescription() {

        List<String> description = new ArrayList<>();

        description.add("Mkdir command.");
        description.add("It takes a single argument which has to be directory name.");
        description.add("It creates the appropriate directory structure.");

        return description;

    }
}
