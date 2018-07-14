package hr.fer.zemris.java.hw05.db;

/**
 * This class represents FieldValueGetters. It contains 3 public static final implementations of {@link IFieldValueGetter} interface.
 */
public class FieldValueGetters {

    /**
     * Getter for first name.
     */
    public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;

    /**
     * Getter for last name.
     */
    public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;

    /**
     * Getter for jmbag.
     */
    public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
