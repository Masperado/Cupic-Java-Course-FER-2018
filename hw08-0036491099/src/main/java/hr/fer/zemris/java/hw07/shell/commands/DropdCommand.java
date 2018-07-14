package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents dropd command.
 * It poppes directory from stack.
 * Directory can be pushed with "pushd" command.
 */
public class DropdCommand implements ShellCommand {


    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Dropd command must be called without arguments!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Stack<Path> cdStack = (Stack<Path>) env.getSharedData("cdstack");

        if (cdStack == null || cdStack.isEmpty()) {
            env.writeln("Cd stack is empty!");
        } else {
            cdStack.pop();
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "dropd";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Dropd command.");
        description.add("It poppes directory from stack.");
        description.add("Directory can be pushed with \"pushd\" command.");

        return description;
    }
}
