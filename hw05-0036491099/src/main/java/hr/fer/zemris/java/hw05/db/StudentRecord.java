package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class represents StudentRecord. It is uses for storing information about one student.
 */
public class StudentRecord {

    /**
     * Jmbag of student.
     */
    private String jmbag;

    /**
     * Last name of student.
     */
    private String lastName;

    /**
     * First name of student.
     */
    private String firstName;

    /**
     * Final grade of student.
     */
    private String finalGrade;

    /**
     * Basic constructor.
     *
     * @param jmbag      jmbag of student
     * @param lastName   last name of student
     * @param firstName  first name of student
     * @param finalGrade final grade of student
     */
    public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    /**
     * Getter for jmbag.
     *
     * @return Jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Getter for last name.
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for first name.
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for final grade.
     *
     * @return Final grade
     */
    public String getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return Objects.equals(jmbag, that.jmbag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jmbag);
    }
}
