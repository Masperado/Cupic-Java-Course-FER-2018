package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * This interface represents NameBuilderInfo. It is used as a support from {@link NameBuilder}. Here name will be
 * actually built.
 */
public interface NameBuilderInfo {

    /**
     * Getter for string builder.
     *
     * @return string builder
     */
    StringBuilder getStringBuilder();

    /**
     * Getter for matcher group at given index.
     *
     * @param index Index of group
     * @return Group at given index
     */
    String getGroup(int index);
}
