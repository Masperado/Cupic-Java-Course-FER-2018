package hr.fer.zemris.java.hw05.db;

/**
 * This interface represents FieldValueGetter. It defines one method, get, which is used for getting specific field from {@link StudentRecord}.
 */
public interface IFieldValueGetter {

    /**
     * This method is used for getting specified field from {@link StudentRecord}.
     *
     * @param record {@link StudentRecord}
     * @return Specified field from {@link StudentRecord}
     */
    public String get(StudentRecord record);
}
