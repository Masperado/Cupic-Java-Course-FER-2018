package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This class represents listd command.
 * It lists all paths currently on stack.
 * Directory can be pushed to stack with "pushd" command.
 */
public class ListdCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Listd command must be called without arguments!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Stack<Path> cdStack = (Stack<Path>) env.getSharedData("cdstack");

        if (cdStack == null || cdStack.isEmpty()) {
            env.writeln("Cd stack is empty!");
        } else {
            List<Path> cdList = new ArrayList<>(cdStack);
            Collections.reverse(cdList);

            for (Path dir : cdList) {
                env.writeln(dir.toString());
            }
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;

    }

    @Override
    public String getCommandName() {
        return "listd";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Listd command.");
        description.add("It lists all paths currently on stack.");
        description.add("Directory can be pushed to stack with \"pushd\" command.");

        return description;
    }
}
