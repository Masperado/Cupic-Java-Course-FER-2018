package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents rmtree command.
 * It is used for deleting folder and all it contents
 * You must confirm deleting of folder by typing last three characters of folder name
 * Files will be permamently deleted, they wont be put in Trash!
 * Author of this command deleted all data on his PC while developing this command!
 * Be careful!!!
 */
public class RmtreeCommand implements ShellCommand {

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

        Path rmDirectory = env.getCurrentDirectory().resolve(directoryString);

        env.writeln("Are you sure you want to remove: " + rmDirectory.toString());

        env.writeln("If you are type YES followed by space, followed by last three characters of path you want to " +
                "delete: ");
        env.write(env.getPromptSymbol() + " ");

        String input = env.readLine();

        String correct = "YES " + rmDirectory.toString().substring(rmDirectory.toString().length() - 3);

        System.out.println(correct);

        if (!input.equals(correct)) {
            env.writeln("Wise choice.");
        } else {
            try {
                Files.walk(rmDirectory).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                env.writeln("Directory deleted.");
            } catch (IOException e) {
                env.writeln("Error while deleting!");
            }
        }
        env.write(env.getPromptSymbol() + " ");

        return ShellStatus.CONTINUE;

    }

    @Override
    public String getCommandName() {
        return "rmtree";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Rmtree command.");
        description.add("It is used for deleting folder and all it contents");
        description.add("You must confirm deleting of folder by typing last three characters of folder name");
        description.add("Files will be permamently deleted, they wont be put in Trash!");
        description.add("Author of this command deleted all data on his PC while developing this command!");
        description.add("Be careful!!!");

        return description;

    }
}
