package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * This interface represents NameBuilder. It is used for building name of file.
 */
public interface NameBuilder {

    /**
     * This method is used for building name of file.
     *
     * @param info {@link NameBuilderInfo} in which name will be built
     */
    void execute(NameBuilderInfo info);

}
