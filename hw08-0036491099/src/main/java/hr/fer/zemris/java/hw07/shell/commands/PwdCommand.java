package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents Pwd command.
 * It is used for printing working directory.
 */
public class PwdCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Pwd command must be called without arguments!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }


        env.writeln(env.getCurrentDirectory().toString());
        env.write(env.getPromptSymbol() + " ");

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "pwd";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Pwd command.");
        description.add("It is used for printing working directory.");

        return description;
    }
}
