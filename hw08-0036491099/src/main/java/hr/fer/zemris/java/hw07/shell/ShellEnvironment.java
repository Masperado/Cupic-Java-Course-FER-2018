package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.*;
import hr.fer.zemris.java.hw07.shell.commands.massrename.MassrenameCommand;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class represents implementation of {@link Environment} that is used in {@link MyShell}.
 */
public class ShellEnvironment implements Environment {

    /**
     * Scanner for reading from console.
     */
    private Scanner sc = new Scanner(System.in);

    /**
     * Multiline symbol.
     */
    private Character multiLine = '|';

    /**
     * Prompt symbol.
     */
    private Character prompt = '>';

    /**
     * More line symbol.
     */
    private Character moreLine = '\\';

    /**
     * {@link SortedMap} of supported commands.
     */
    private SortedMap<String, ShellCommand> commands;

    /**
     * Current directory at which shell is at.
     */
    private Path currentDirectory = Paths.get("").toAbsolutePath().normalize();

    /**
     * Map of shared data.
     */
    private Map<String, Object> sharedData = new HashMap<>();

    /**
     * Basic constructor.
     */
    public ShellEnvironment() {
        System.out.print("Welcome to MyShell v 1.0\n> ");
        this.commands = new TreeMap<>();
        putCommands();
    }

    /**
     * This method is used for putting supported commands to commands map.
     */
    private void putCommands() {
        ShellCommand charset = new CharsetCommand();
        commands.put(charset.getCommandName(), charset);
        ShellCommand symbol = new SymbolCommand();
        commands.put(symbol.getCommandName(), symbol);
        ShellCommand exit = new ExitCommand();
        commands.put(exit.getCommandName(), exit);
        ShellCommand cat = new CatCommand();
        commands.put(cat.getCommandName(), cat);
        ShellCommand copy = new CopyCommand();
        commands.put(copy.getCommandName(), copy);
        ShellCommand ls = new LsCommand();
        commands.put(ls.getCommandName(), ls);
        ShellCommand mkdir = new MkdirCommand();
        commands.put(mkdir.getCommandName(), mkdir);
        ShellCommand hexdump = new HexdumpCommand();
        commands.put(hexdump.getCommandName(), hexdump);
        ShellCommand tree = new TreeCommand();
        commands.put(tree.getCommandName(), tree);
        ShellCommand help = new HelpCommand();
        commands.put(help.getCommandName(), help);
        ShellCommand pwd = new PwdCommand();
        commands.put(pwd.getCommandName(), pwd);
        ShellCommand cd = new CdCommand();
        commands.put(cd.getCommandName(), cd);
        ShellCommand pushd = new PushdCommand();
        commands.put(pushd.getCommandName(), pushd);
        ShellCommand popd = new PopdCommand();
        commands.put(popd.getCommandName(), popd);
        ShellCommand listd = new ListdCommand();
        commands.put(listd.getCommandName(), listd);
        ShellCommand dropd = new DropdCommand();
        commands.put(dropd.getCommandName(), dropd);
        ShellCommand rmtree = new RmtreeCommand();
        commands.put(rmtree.getCommandName(), rmtree);
        ShellCommand cptree = new CptreeCommand();
        commands.put(cptree.getCommandName(), cptree);
        ShellCommand massrename = new MassrenameCommand();
        commands.put(massrename.getCommandName(), massrename);
    }

    @Override
    public String readLine() throws ShellIOException {
        StringBuilder sb = new StringBuilder();
        String line = sc.nextLine();
        while (line.endsWith(moreLine.toString())) {
            sb.append(line, 0, line.length() - 1);
            write(multiLine + " ");
            line = sc.nextLine();
        }
        sb.append(line);
        return sb.toString();
    }

    @Override
    public void write(String text) throws ShellIOException {
        System.out.print(text);
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        System.out.println(text);
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(commands);
    }

    @Override
    public Character getMultilineSymbol() {
        return multiLine;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {
        this.multiLine = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return prompt;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        this.prompt = symbol;
    }

    @Override
    public Character getMorelinesSymbol() {
        return multiLine;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {
        this.moreLine = symbol;
    }

    @Override
    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public void setCurrentDirectory(Path path) {
        if (!Files.exists(path)) {
            throw new ShellIOException("Directory doesn't exist!");
        }

        this.currentDirectory = path;

    }

    @Override
    public Object getSharedData(String key) {
        return sharedData.get(key);
    }

    @Override
    public void setSharedData(String key, Object value) {
        if (key == null) {
            throw new ShellIOException("Key cannot be null!");
        }

        sharedData.put(key, value);
    }
}
