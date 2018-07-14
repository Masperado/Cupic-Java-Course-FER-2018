package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.ShellUtility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents hexdump command. It takes a single argument which has to be file name.
 * It produces hex-output of given file.
 */
public class HexdumpCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String fileString = ShellUtility.parsePath(arguments);

        int currentIndexInArgument = fileString.length();

        if (arguments.startsWith("\"")) {
            currentIndexInArgument += 2;
            currentIndexInArgument += IntStream.range(0, fileString.length()).filter(i -> fileString.charAt(i) == '"' || fileString.charAt(i) == '\\').count();

        }

        if (currentIndexInArgument != arguments.length()) {
            env.writeln("Invalid number of arguments to mkdir function!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        Path filePath = env.getCurrentDirectory().resolve(fileString);

        if (Files.isDirectory(filePath)) {
            env.writeln("Cannot perform hexdump on file");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }

        try (InputStream is = Files.newInputStream(filePath, StandardOpenOption.READ)) {

            byte[] buff = new byte[16];
            int currentPosition = 0x0;

            StringBuilder sb = new StringBuilder();

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;

                sb.append(String.format("%08X", currentPosition));
                sb.append(": ");
                for (int i = 0; i < 16; i++) {
                    if (i >= r) {
                        sb.append("   ");
                    } else {
                        sb.append(String.format("%02X", buff[i])).append(" ");
                    }

                    if (i == 7) {
                        sb.setLength(sb.length() - 1);
                        sb.append("|");
                    }

                }

                sb.append(" | ");

                for (int i = 0; i < r; i++) {
                    if (buff[i] < 32 || buff[i] > 127) {
                        buff[i] = 46;
                    }
                }
                sb.append(new String(buff));

                env.writeln(sb.toString());
                sb.setLength(0);
                currentPosition += 0x10;
            }

        } catch (IOException e) {
            env.writeln("Error while opening file!");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;

        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "hexdump";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Hexdump command.");
        description.add("It takes a single argument which has to be file name.");
        description.add("It produces hex-output of given file.");

        return description;
    }


}
