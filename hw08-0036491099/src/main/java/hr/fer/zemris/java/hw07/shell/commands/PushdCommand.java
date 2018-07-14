package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * This class represents pushd command.
 * It pushes current working directory to stack and changing working directory to directory given in argument.
 * Directory can be popped with "popd" command.
 */
public class PushdCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String directoryString = ShellUtility.parsePath(arguments);

        int currentIndexInArgument = directoryString.length();

        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, directoryString.length()).filter(i -> directoryString.charAt(i) == '"' || directoryString.charAt(i) == '\\').count();

        }

        if (currentIndexInArgument != arguments.length()) {
            env.writeln("Invalid number of arguments to pushd function!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Path newDirectory = env.getCurrentDirectory().resolve(directoryString);

        try {
            Path oldDirectory = env.getCurrentDirectory();

            env.setCurrentDirectory(newDirectory);
            env.writeln("New directory: " + env.getCurrentDirectory());

            Stack<Path> cdStack = (Stack<Path>) env.getSharedData("cdstack");

            if (cdStack == null) {
                cdStack = new Stack<>();
                env.setSharedData("cdstack", cdStack);
            }

            cdStack.push(oldDirectory);

        } catch (ShellIOException ex) {
            env.writeln("Directory doesn't exist!");
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "pushd";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Pushd command.");
        description.add("It pushes current working directory to stack and changing working directory to directory " +
                "given in argument.");
        description.add("Directory can be popped with \"popd\" command.");

        return description;
    }
}
