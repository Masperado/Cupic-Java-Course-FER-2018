package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents Popd command.
 * It poppes directory from stack and sets that directory as current working directory.
 * Directory can be pushed with "pushd" command.
 */
public class PopdCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Popd command must be called without arguments!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Stack<Path> cdStack = (Stack<Path>) env.getSharedData("cdstack");

        if (cdStack == null || cdStack.isEmpty()) {
            env.writeln("Cd Stack is empty!");

        } else {
            try {
                env.setCurrentDirectory(cdStack.pop());
                env.writeln("New directory is: " + env.getCurrentDirectory());
            } catch (ShellIOException ex) {
                env.writeln("Directory doesn't exist");
            }
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;


    }

    @Override
    public String getCommandName() {
        return "popd";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Popd command.");
        description.add("It poppes directory from stack and sets that directory as current working directory.");
        description.add("Directory can be pushed with \"pushd\" command.");

        return description;
    }
}
