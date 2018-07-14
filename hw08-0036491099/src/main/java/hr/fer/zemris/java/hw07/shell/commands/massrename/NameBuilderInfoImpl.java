package hr.fer.zemris.java.hw07.shell.commands.massrename;

import hr.fer.zemris.java.hw07.shell.ShellIOException;

import java.util.regex.Matcher;

/**
 * This class represents implementation of {@link NameBuilderInfo}.
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

    /**
     * Matcher.
     */
    private Matcher matcher;

    /**
     * String builder.
     */
    private StringBuilder sb = new StringBuilder();

    /**
     * Basic constructor.
     *
     * @param matcher matcher
     */
    public NameBuilderInfoImpl(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public StringBuilder getStringBuilder() {
        return sb;
    }

    @Override
    public String getGroup(int index) {
        try {
            return matcher.group(index);
        } catch (IndexOutOfBoundsException ex) {
            throw new ShellIOException("Group out of bounds!");
        }
    }

}
