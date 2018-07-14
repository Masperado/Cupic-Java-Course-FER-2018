package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents cptree command.
 * It is used for copying folder and all it contents.
 */
public class CptreeCommand implements ShellCommand {

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

        Path source = env.getCurrentDirectory().resolve(sourceString);
        Path destination = env.getCurrentDirectory().resolve(destinationString);

        if (!Files.isDirectory(source)) {
            env.writeln("Source isn't directory!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        if (Files.isDirectory(destination)) {
            destination = destination.resolve(source.getFileName());
        } else {
            if (!Files.isDirectory(destination.resolve("..").normalize())) {
                env.writeln("Destination isn't a directory!");
                env.write(env.getPromptSymbol() + " ");
                return ShellStatus.CONTINUE;
            }
        }

        if (source.equals(destination)) {
            env.writeln("Cannot copy directory to itself!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }


        try {
            copy(source, destination);
            env.writeln("Directory copied.");
        } catch (IOException e) {
            env.writeln("Error while copying!");
        }


        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;

    }

    private void copy(Path source, Path destination) throws IOException {
        if (Files.isDirectory(source)) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    private void copyDirectory(Path sourceDirectory, Path destinationDirectory) throws IOException {
        if (!Files.exists(destinationDirectory)) {
            Files.createDirectories(destinationDirectory);
        }

        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(sourceDirectory);
        for (Path path : directoryStream) {
            copy(sourceDirectory.resolve(path.getFileName()), destinationDirectory.resolve(path.getFileName()));
        }

    }

    private void copyFile(Path source, Path destination) throws IOException {


        InputStream is = Files.newInputStream(source, StandardOpenOption.READ);
        OutputStream os = Files
                .newOutputStream(destination, StandardOpenOption.CREATE);

        byte[] buff = new byte[4096];
        while (true) {
            int r = is.read(buff);
            if (r < 1) break;
            os.write(Arrays.copyOf(buff, r));

        }

        is.close();
        os.close();
    }

    @Override
    public String getCommandName() {
        return "cptree";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Cptree command.");
        description.add("It is used for copying folder and all it contents.");

        return description;
    }
}
