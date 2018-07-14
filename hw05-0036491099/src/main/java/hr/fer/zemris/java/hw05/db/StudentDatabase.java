package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents StudentDatabase. It is used for storing data about students. It uses {@link StudentRecord} for storing records.
 * Data is loaded through List of Strings given in constructor. Database supports returning record for given jmbag in O(1) complexity and filtering
 * records for given {@link IFilter} in O(n) complexity.
 */
public class StudentDatabase {

    /**
     * List of student records.
     */
    private List<StudentRecord> recordsList;

    /**
     * Hashtable of student records.
     */
    private SimpleHashtable<String, StudentRecord> recordsMap;

    /**
     * Basic constructor.
     *
     * @param records String list of students
     */
    public StudentDatabase(List<String> records) {
        this.recordsList = new ArrayList<>(records.size());
        this.recordsMap = new SimpleHashtable<>(records.size());
        parseRecords(records);
    }

    /**
     * This method is used for parsing student records into database from List of Strings.
     *
     * @param records String list of Student records
     */
    private void parseRecords(List<String> records) {
        for (String record : records) {
            String[] parts = record.split("\t");

            if (parts.length != 4) {
                throw new RuntimeException("Zapisi studenata nisu dobri!");
            }

            StudentRecord studentRecord = new StudentRecord(parts[0], parts[1], parts[2], parts[3]);

            recordsList.add(studentRecord);
            recordsMap.put(studentRecord.getJmbag(), studentRecord);
        }
    }

    /**
     * This method returns {@link StudentRecord} for given JMBAG.
     *
     * @param jmbag {@link StudentRecord} jmbag
     * @return {@link StudentRecord} for given jmbag
     */
    public StudentRecord forJMBAG(String jmbag) {
        return recordsMap.get(jmbag);
    }

    /**
     * This method is used for filter database with given filter.
     *
     * @param filter {@link IFilter}
     * @return Filtered database
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filtered = new ArrayList<>();

        for (StudentRecord studentRecord : recordsList) {
            if (filter.accepts(studentRecord)) {
                filtered.add(studentRecord);
            }
        }

        return filtered;
    }
}
