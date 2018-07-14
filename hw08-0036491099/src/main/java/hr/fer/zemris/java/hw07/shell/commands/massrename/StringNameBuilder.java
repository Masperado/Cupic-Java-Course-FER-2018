package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * This class represents implementation of {@link NameBuilder} that is used for writing strings to {@link NameBuilderInfo}.
 */
public class StringNameBuilder implements NameBuilder {

    /**
     * String that will be written.
     */
    private String string;

    /**
     * Basic constructor.
     *
     * @param string String
     */
    public StringNameBuilder(String string) {
        this.string = string;
    }

    @Override
    public void execute(NameBuilderInfo info) {
        info.getStringBuilder().append(string);
    }
}
