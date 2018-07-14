package hr.fer.zemris.java.hw05.db;

/**
 * This class represents Filter. It is used for filtering {@link StudentRecord}. It defines one method, accepts,
 * which return boolean value if this filter accepts given record.
 */
public interface IFilter {

    /**
     * This method checks if given {@link StudentRecord} passed this filter.
     *
     * @param record {@link StudentRecord}
     * @return True if passed, false otherwise
     */
    public boolean accepts(StudentRecord record);
}
