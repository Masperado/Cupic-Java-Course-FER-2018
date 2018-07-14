package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * This class represents {@link NameBuilder} that is used for building string from matcher groups.
 */
public class GroupNameBuilder implements NameBuilder {

    /**
     * Group number.
     */
    private int groupNumber;

    /**
     * Minimum length.
     */
    private int minLength;

    /**
     * Zeroed flag.
     */
    private boolean zeroed;

    /**
     * Only group flag.
     */
    private boolean onlyGroup;

    /**
     * Constructor with only group number.
     *
     * @param groupNumber group number
     */
    public GroupNameBuilder(int groupNumber) {
        this.groupNumber = groupNumber;
        this.onlyGroup = true;
    }

    /**
     * Basic consrtuctor.
     *
     * @param groupNumber group number
     * @param minLength   minimum length
     * @param zeroed      zeroed flag
     */
    public GroupNameBuilder(int groupNumber, int minLength, boolean zeroed) {
        this.groupNumber = groupNumber;
        this.minLength = minLength;
        this.zeroed = zeroed;
    }

    @Override
    public void execute(NameBuilderInfo info) {
        StringBuilder group = new StringBuilder(info.getGroup(groupNumber));

        if (!onlyGroup) {
            while (group.length() < minLength) {
                if (zeroed) {
                    group.insert(0, "0");
                } else {
                    group.insert(0, " ");
                }
            }
        }

        info.getStringBuilder().append(group.toString());

    }
}
