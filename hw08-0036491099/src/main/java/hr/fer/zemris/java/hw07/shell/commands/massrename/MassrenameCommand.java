package hr.fer.zemris.java.hw07.shell.commands.massrename;

import hr.fer.zemris.java.hw07.shell.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * This class represents massrename command.
 * It is used for renaming all contents of folder and moving it to another directory.
 * Command has four subcommands and one of them must be given as an argument.
 * Subcommands are: filter,groups,show and execute.
 * Files will be selected with Pattern regex which must also be provided as argument.
 * Files will be renamed with Izraz regex.
 */
public class MassrenameCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        // Parse source

        String sourceString = ShellUtility.parsePath(arguments);
        int currentIndexInArgument = sourceString.length();
        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, sourceString.length()).filter(i -> sourceString.charAt(i) == '"' || sourceString.charAt(i) == '\\').count();

        }
        currentIndexInArgument++;

        // Parse destination
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


        // Parse command
        String command = arguments.substring(currentIndexInArgument).trim().split(" ")[0];
        currentIndexInArgument += command.length() + 1;


        // Parse mask
        String maskArguments = arguments.substring(currentIndexInArgument).trim();
        String maskString = ShellUtility.parsePath(maskArguments);
        currentIndexInArgument += maskString.length();
        if (maskArguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, maskString.length()).filter(i -> maskString
                    .charAt(i) == '"'
                    || maskString.charAt(i) == '\\').count();
        }
        currentIndexInArgument++;


        // Parse izraz
        String izrazString = null;
        if (command.equals("execute") || command.equals("show")) {
            String izrazArguments = arguments.substring(currentIndexInArgument).trim();
            izrazString = ShellUtility.parsePath(izrazArguments);
            currentIndexInArgument += izrazString.length();

            final String lambdaString = izrazString;
            if (izrazArguments.startsWith("\"")) {
                currentIndexInArgument += 2;
                currentIndexInArgument += IntStream.range(0, izrazString.length()).filter(i -> lambdaString
                        .charAt(i) == '"'
                        || lambdaString.charAt(i) == '\\').count();
            }
            currentIndexInArgument++;
        }


        // Check if there are more arguments
        if (arguments.length() > currentIndexInArgument) {
            env.writeln("Nepravilan broj argumenata massrename funkciji!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }


        // Convert to dir
        Path source = env.getCurrentDirectory().resolve(sourceString);
        Path destination = env.getCurrentDirectory().resolve(destinationString);
        if (!Files.isDirectory(source) || !Files.isDirectory(destination)) {
            env.writeln("Predani argumenti massrename nisu direktoriji!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        // Execute command for every file in source directory
        Pattern pattern = Pattern.compile(maskString);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(source)) {
            for (Path path : directoryStream) {
                Matcher m = pattern.matcher(path.getFileName().toString());
                if (m.matches()) {
                    switch (command) {
                        case "filter":
                            env.writeln(path.getFileName().toString());
                            break;
                        case "groups":
                            env.write(path.getFileName().toString());
                            for (int i = 0; i <= m.groupCount(); i++) {
                                env.write(" " + i + ": ");
                                env.write(m.group(i));
                            }
                            env.writeln("");
                            break;
                        case "show":
                        case "execute":
                            NameBuilderParser parser = new NameBuilderParser(izrazString);
                            NameBuilder builder = parser.getNameBuilder();
                            NameBuilderInfo info = new NameBuilderInfoImpl(m);
                            builder.execute(info);
                            String newName = info.getStringBuilder().toString();
                            if (command.equals("show")) {
                                env.writeln(path.getFileName().toString() + " => " + newName);
                            } else {
                                Path movePath = destination.resolve(newName);
                                Files.move(path, movePath, StandardCopyOption.REPLACE_EXISTING);
                                env.writeln(sourceString + "/" + path.getFileName().toString() + " => " +
                                        destinationString + "/" + newName);
                            }

                            break;
                        default:
                            env.writeln("Invalid massrename function!");
                            env.write(env.getPromptSymbol() + " ");
                    }
                }
            }
        } catch (IOException ex) {
            env.writeln("Error while reading from directory!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        } catch (ShellIOException ex) {
            env.writeln(ex.getMessage());
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "massrename";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Massrename command.");
        description.add("It is used for renaming all contents of folder and moving it to another directory.");
        description.add("Command has four subcommands and one of them must be given as an argument.");
        description.add("Subcommands are: filter,groups,show and execute.");
        description.add("Files will be selected with Pattern regex which must also be provided as argument.");
        description.add("Files will be renamed with Izraz regex.");

        return description;
    }


}
