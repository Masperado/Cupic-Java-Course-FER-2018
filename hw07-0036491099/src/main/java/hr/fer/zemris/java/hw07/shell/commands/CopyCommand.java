package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents copy command. It expects two arguments: source file name and destination file name.
 * If destination file exists, you should ask user is it allowed to overwrite it.
 * If the second argument is directory, you should assume that user wants to copy the original file into that directory using the original file name.
 */
public class CopyCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String sourceString = ShellUtility.parsePath(arguments);

        int currentIndexInArgument = sourceString.length();

        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, sourceString.length()).filter(i -> sourceString.charAt(i) == '"' || sourceString.charAt(i) == '\\').count();

        }

        String destinationArguments = arguments.substring(currentIndexInArgument).trim();

        String destinationString = ShellUtility.parsePath(destinationArguments);

        currentIndexInArgument += destinationString.length();

        if (destinationArguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, destinationString.length()).filter(i -> destinationString
                    .charAt(i) == '"'
                    || destinationString.charAt(i) == '\\').count();

        }

        currentIndexInArgument++;

        if (arguments.length() > currentIndexInArgument) {
            env.writeln("Invalid number of arguments to copy function!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Path source = Paths.get(sourceString);
        Path destination = Paths.get(destinationString);

        if (Files.isDirectory(destination)) {
            destination = Paths.get(destinationString + "/" + sourceString);
        }

        if (source.equals(destination)) {
            env.writeln("Cannot copy file to itself!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        if (Files.exists(destination)) {
            env.writeln("Do you want to overwrite file?(Y/N)");
            env.write(env.getPromptSymbol() + " ");
            String answer = env.readLine();
            if (!(answer.equals("Y"))) {
                env.writeln("File will not be copied!");
                env.write(env.getPromptSymbol() + " ");
                return ShellStatus.CONTINUE;
            }
        }

        try (InputStream is = Files.newInputStream(source, StandardOpenOption.READ); OutputStream os = Files
                .newOutputStream(destination, StandardOpenOption.CREATE)) {
            byte[] buff = new byte[4096];
            while (true) {
                int r = is.read(buff);
                if (r < 1) break;
                os.write(Arrays.copyOf(buff, r));
            }

        } catch (IOException e) {
            env.writeln("Error while opening files!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        env.writeln("File copied.");
        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;

    }

    @Override
    public String getCommandName() {
        return "copy";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Copy command.");
        description.add("Expects two arguments: source file name and destination file name.");
        description.add("If destination file exists, you should ask user is it allowed to overwrite it.");
        description.add("If the second argument is directory, you should assume that user wants to copy the original file into that directory using the original file name.");

        return description;

    }
}
