package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents tree command. It takes a single argument which has to be directory name.
 * It prints a tree (each directory level shifts output two characters to the right).
 */
public class TreeCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String pathString = ShellUtility.parsePath(arguments);

        int currentIndexInArgument = pathString.length();

        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, pathString.length()).filter(i -> pathString.charAt(i) == '"'
                    || pathString.charAt(i) == '\\').count();

        }

        if (currentIndexInArgument != arguments.length()) {
            env.writeln("Invalid number of arguments to tree command!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Path directoryPath = env.getCurrentDirectory().resolve(pathString);

        if (!Files.isDirectory(directoryPath)) {
            env.writeln("Argument given isn't directory!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.walkFileTree(directoryPath, new FileVisitor<>() {
                int depth = 1;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    env.writeln(String.format("%" + depth + "s%s", "", dir.getFileName()));
                    depth += 2;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    env.writeln(String.format("%" + depth + "s%s", "", file.getFileName()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    depth -= 2;
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            env.writeln("Error while reading from directory!");
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "tree";
    }

    @Override
    public List<String> getCommandDescription() {

        List<String> description = new ArrayList<>();

        description.add("Tree command.");
        description.add("It takes a single argument which has to be directory name.");
        description.add("It prints a tree (each directory level shifts output two characters to the right).");

        return description;

    }
}
