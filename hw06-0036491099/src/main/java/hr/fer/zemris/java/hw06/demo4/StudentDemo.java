package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used for demonstrating uses of {@link java.util.stream.Stream} api.
 */
public class StudentDemo {

    /**
     * Main method.
     *
     * @param args Command line arguments
     * @throws IOException If given path to student database is invalid
     */
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("studenti.txt"));

        List<StudentRecord> records = convert(lines);

        long broj = vratiBodovaViseOd25(records);
        System.out.println(broj);
        System.out.println();

        long broj5 = vratiBrojOdlikasa(records);
        System.out.println(broj5);
        System.out.println();

        List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
        for (StudentRecord record : odlikasi) {
            System.out.println(record);
        }
        System.out.println();

        List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
        for (StudentRecord record : odlikasiSortirano) {
            System.out.println(record);
        }
        System.out.println();

        List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
        for (String jmbag : nepolozeniJMBAGovi) {
            System.out.println(jmbag);
        }
        System.out.println();

        Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
        for (Integer key : mapaPoOcjenama.keySet()) {
            System.out.println(mapaPoOcjenama.get(key).size());
        }
        System.out.println();

        Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
        for (Integer key : mapaPoOcjenama2.keySet()) {
            System.out.println(mapaPoOcjenama2.get(key));
        }
        System.out.println();

        Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
        for (Boolean key : prolazNeprolaz.keySet()) {
            if (key) {
                System.out.print("Prošli: ");
            } else {
                System.out.print("Pali: ");
            }
            System.out.println(prolazNeprolaz.get(key).size());
        }
        System.out.println();


    }

    /**
     * This method is used for converting list of strings to list of {@link StudentRecord}.
     *
     * @param lines list of string that will be converted
     * @return list of {@link StudentRecord}
     */
    private static List<StudentRecord> convert(List<String> lines) {
        List<StudentRecord> records = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split("\\s+");
            records.add(new StudentRecord(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
                    Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Integer.parseInt(parts[6])));
        }

        return records;
    }

    /**
     * This method is used for returning number of students that had more than 25 total points.
     *
     * @param records List of {@link StudentRecord}
     * @return Number of students that had more than 25 total points
     */
    public static long vratiBodovaViseOd25(List<StudentRecord> records) {
        return records.stream().filter(studentRecord -> (studentRecord.getBodMI() + studentRecord.getBodZI() + studentRecord.bodLAB) > 25).count();
    }

    /**
     * This method is used for returning list of students that had more than 25 total points.
     *
     * @param records List of {@link StudentRecord}
     * @return List of students that had more than 25 total points
     */
    public static long vratiBrojOdlikasa(List<StudentRecord> records) {
        return records.stream().filter(studentRecord -> studentRecord.getGrade() == 5).count();
    }

    /**
     * This method is used for returning list of students that had grade 5.
     *
     * @param records List of {@link StudentRecord}
     * @return List of students that had grade 5
     */
    public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
        return records.stream().filter(studentRecord -> studentRecord.getGrade() == 5).collect(Collectors.toList());
    }

    /**
     * This method is used for returning list of students that had grade 5 sorted by their score.
     *
     * @param records List of {@link StudentRecord}
     * @return List of students that had grade 5 sorted by their score
     */
    public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
        return records.stream().filter(studentRecord -> studentRecord.getGrade() == 5).sorted((o2, o1) -> Double
                .compare(o1.getBodMI() + o1.getBodZI() + o1.getBodLAB(), o2.bodMI + o2.getBodZI() + o2.getBodLAB())).collect
                (Collectors
                        .toList());
    }

    /**
     * This method is used for returning list of student's jmbags that had failed the class.
     *
     * @param records List of {@link StudentRecord}
     * @return List of jmbags of students that had failed the class
     */
    public static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
        return records.stream().filter(studentRecord -> studentRecord.getGrade() == 1).map(StudentRecord::getJmbag).sorted
                (String::compareTo).collect(Collectors.toList());
    }

    /**
     * This method is used for returning map of which key are grade and value are list of students that had got
     * that grade.
     *
     * @param records List of {@link StudentRecord}
     * @return Map
     */
    public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
        return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
    }

    /**
     * This method is used for returning map of which key are grade and value are number of students that had got
     * that grade.
     *
     * @param records List of {@link StudentRecord}
     * @return Map
     */
    public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
        return records.stream().collect(Collectors.toMap(StudentRecord::getGrade, a -> 1, (o1, o2) -> o1 + o2));
    }

    /**
     * This method is used for returning map of which key are boolean value if student had passed or failed the class
     * and value are number of students that passed or failed.
     *
     * @param records List of {@link StudentRecord}
     * @return Map
     */
    public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
        return records.stream().collect(Collectors.partitioningBy(o -> o.getGrade() > 1));
    }


}
