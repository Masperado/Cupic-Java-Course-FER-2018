package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents ls command. It takes a single argument which has to be directory name.
 * It writes directory listing(not recursive)
 * The output consists of 4 columns.
 * First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).
 * Second column contains object size in bytes that is right aligned and occupies 10 characters.
 * Third column is creation date/time and fourth column is file name.
 */
public class LsCommand implements ShellCommand {

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
            env.writeln("Invalid number of arguments to ls command!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Path directoryPath = Paths.get(pathString);

        if (!Files.isDirectory(directoryPath)) {
            env.writeln("Argument given isn't directory!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        long maxLengthFile = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path path : directoryStream) {
                if (Files.size(path) > maxLengthFile) {
                    maxLengthFile = Files.size(path);
                }
            }

        } catch (IOException ex) {
            env.writeln("Error while reading from directory!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {

            int maxLengthStringLength = String.valueOf(maxLengthFile).length();

            for (Path path : directoryStream) {
                StringBuilder sb = new StringBuilder();

                if (Files.isDirectory(path)) {
                    sb.append("d");
                } else {
                    sb.append("-");
                }

                if (Files.isReadable(path)) {
                    sb.append("r");
                } else {
                    sb.append("-");
                }

                if (Files.isWritable(path)) {
                    sb.append("w");
                } else {
                    sb.append("-");
                }

                if (Files.isExecutable(path)) {
                    sb.append("x");
                } else {
                    sb.append("-");
                }

                sb.append(" ");

                long fileSize = Files.size(path);
                sb.append(String.format("%" + maxLengthStringLength + "d", fileSize));
                sb.append(" ");


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                BasicFileAttributeView faView;
                faView = Files.getFileAttributeView(
                        path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
                );
                BasicFileAttributes attributes = faView.readAttributes();
                FileTime fileTime = attributes.creationTime();
                String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

                sb.append(formattedDateTime);
                sb.append(" ");
                sb.append(path.getFileName());
                env.writeln(sb.toString());

            }
        } catch (IOException ex) {
            env.writeln("Error while reading from directory!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Ls command.");
        description.add("It takes a single argument which has to be directory name.");
        description.add("It writes directory listing(not recursive)");
        description.add("The output consists of 4 columns.");
        description.add("First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
        description.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
        description.add("Third column is creation date/time and fourth column is file name.");


        return description;
    }
}
