package hr.fer.zemris.java.hw06.demo4;

/**
 * This class is used for representing student record.
 */
public class StudentRecord {

    /**
     * Student jmbag.
     */
    public String jmbag;

    /**
     * Student surname.
     */
    public String prezime;

    /**
     * Student name.
     */
    public String ime;

    /**
     * Students points on MI.
     */
    public double bodMI;

    /**
     * Student points on ZI.
     */
    public double bodZI;

    /**
     * Student points in LAB.
     */
    public double bodLAB;

    /**
     * Student grade.
     */
    public int grade;

    /**
     * Basic constructor.
     *
     * @param jmbag   jmbag
     * @param prezime surname
     * @param ime     name
     * @param bodMI   points on MI
     * @param bodZI   points on ZI
     * @param bodLAB  points on LAB
     * @param grade   grade
     */
    public StudentRecord(String jmbag, String prezime, String ime, double bodMI, double bodZI, double bodLAB, int grade) {
        this.jmbag = jmbag;
        this.prezime = prezime;
        this.ime = ime;
        this.bodMI = bodMI;
        this.bodZI = bodZI;
        this.bodLAB = bodLAB;
        this.grade = grade;
    }

    /**
     * Getter for jmbag.
     *
     * @return jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Getter for surname.
     *
     * @return surname
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public String getIme() {
        return ime;
    }

    /**
     * Getter for points on MI.
     *
     * @return points on MI
     */
    public double getBodMI() {
        return bodMI;
    }

    /**
     * Getter for points of ZI.
     *
     * @return points on ZI
     */
    public double getBodZI() {
        return bodZI;
    }

    /**
     * Getter for points in LAB.
     *
     * @return points in LAB
     */
    public double getBodLAB() {
        return bodLAB;
    }

    /**
     * Getter for grade.
     *
     * @return grade
     */
    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "StudentRecord{" +
                "jmbag='" + jmbag + '\'' +
                ", prezime='" + prezime + '\'' +
                ", ime='" + ime + '\'' +
                ", bodMI=" + bodMI +
                ", bodZI=" + bodZI +
                ", bodLAB=" + bodLAB +
                ", grade=" + grade +
                '}';
    }
}
