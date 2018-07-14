package hr.fer.zemris.java.hw07.shell.commands.massrename;

import java.util.List;

/**
 * This class represents implementation of {@link NameBuilder} that is used for composing multiple {@link NameBuilder}.
 */
public class CompositionNameBuilder implements NameBuilder {

    /**
     * NameBuilders that will be executed.
     */
    private List<NameBuilder> builders;

    /**
     * Basic constructor.
     *
     * @param builders {@link NameBuilder}
     */
    public CompositionNameBuilder(List<NameBuilder> builders) {
        this.builders = builders;
    }

    @Override
    public void execute(NameBuilderInfo info) {
        for (NameBuilder builder : builders) {
            builder.execute(info);
        }
    }
}
