package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents cat command. It takes up to two arguments.
 * The first argument is path to some file and is mandatory.
 * The second argument is charset name that should be used to interpret chars from bytes.
 * If not provided, a default platform charset should be used.
 * This command opens given file and writes its content to console.
 */
public class CatCommand implements ShellCommand {


    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String pathString = ShellUtility.parsePath(arguments);

        Charset charset = Charset.defaultCharset();
        Path path;

        int currentIndexInArgument = pathString.length();

        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, pathString.length()).filter(i -> pathString.charAt(i) == '"'
                    || pathString.charAt(i) == '\\').count();

        }

        if (currentIndexInArgument >= arguments.length()) {
            path = Paths.get(pathString);
        } else {
            String charsetString = arguments.substring(currentIndexInArgument).trim();

            path = Paths.get(pathString);

            try {
                charset = Charset.forName(charsetString);
            } catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
                env.writeln("Invalid charset!");
                env.write(env.getPromptSymbol() + " ");
                return ShellStatus.CONTINUE;
            }
        }

        try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
            byte[] buff = new byte[4096];

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;

                String output = new String(Arrays.copyOf(buff, r), charset);
                env.write(output);
            }
        } catch (IOException e) {
            env.writeln("Error while opening files!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }
        env.writeln("");
        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;

    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Cat command.");
        description.add("It takes up to two arguments.");
        description.add("The first argument is path to some file and is mandatory.");
        description.add("The second argument is charset name that should be used to interpret chars from bytes.");
        description.add("If not provided, a default platform charset should be used.");
        description.add("This command opens given file and writes its content to console.");

        return description;

    }
}
