package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used for viewing {@link StudentDatabase}. It supports two commands, "query" and "exit". Query expressions
 * must be entered in format (fieldName)(operator)(string literal).
 */
public class StudentDatabaseViewer {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        List<String> lines = null;

        try {
            lines = Files.readAllLines(
                    Paths.get("database.txt"),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.out.println("Baza se ne može učitati iz datoteke!");
            System.exit(1);
        }

        StudentDatabase database = new StudentDatabase(lines);
        Scanner sc = new Scanner(System.in);
        String line;

        while (true) {
            System.out.print("> ");
            line = sc.nextLine();
            if (line.trim().equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (!line.trim().startsWith("query")) {
                System.out.println("Invalid command!");
                continue;
            }

            QueryParser parser;
            try {
                parser = new QueryParser(line.trim().substring(5));
            } catch (RuntimeException ex) {
                System.out.println("Neispravan izraz!");
                continue;
            }
            List<StudentRecord> records;

            if (parser.isDirectQuery()) {
                records = new ArrayList<>();
                StudentRecord record = database.forJMBAG(parser.getQueriedJmbag());
                if (record != null) {
                    records.add(record);
                }

            } else {
                records = database.filter(new QueryFilter(parser.getQuery()));
            }

            if (records.size() > 0) {
                System.out.println(transformRecordsForOutput(records));
            }
            System.out.println("Records selected: " + records.size() + "\n");
        }

        sc.close();
    }

    /**
     * This method is used for transforming given List of {@link StudentRecord} into String that can be outputted to console.
     *
     * @param records List of {@link StudentRecord}
     * @return String for output
     */
    private static String transformRecordsForOutput(List<StudentRecord> records) {
        int longestJmbagLength = 0;
        int longestLastNameLength = 0;
        int longestFirstNameLength = 0;
        int longestFinalGradeLength = 0;

        for (StudentRecord record : records) {
            if (record.getJmbag().length() > longestJmbagLength) {
                longestJmbagLength = record.getJmbag().length();
            }
            if (record.getLastName().length() > longestLastNameLength) {
                longestLastNameLength = record.getLastName().length();
            }
            if (record.getFirstName().length() > longestFirstNameLength) {
                longestFirstNameLength = record.getFirstName().length();
            }
            if (record.getFinalGrade().length() > longestFinalGradeLength) {
                longestFinalGradeLength = record.getFinalGrade().length();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("+");
        sb.append(new String(new char[longestJmbagLength + 2]).replace("\0", "="));
        sb.append("+");
        sb.append(new String(new char[longestLastNameLength + 2]).replace("\0", "="));
        sb.append("+");
        sb.append(new String(new char[longestFirstNameLength + 2]).replace("\0", "="));
        sb.append("+");
        sb.append(new String(new char[longestFinalGradeLength + 2]).replace("\0", "="));
        sb.append("+\n");

        for (StudentRecord record : records) {
            sb.append("| ");
            sb.append(record.getJmbag());
            sb.append(new String(new char[longestJmbagLength - record.getJmbag().length()]).replace("\0", " "));
            sb.append(" | ");
            sb.append(record.getLastName());
            sb.append(new String(new char[longestLastNameLength - record.getLastName().length()]).replace("\0", " "));
            sb.append(" | ");
            sb.append(record.getFirstName());
            sb.append(new String(new char[longestFirstNameLength - record.getFirstName().length()]).replace("\0", " "));
            sb.append(" | ");
            sb.append(record.getFinalGrade());
            sb.append(new String(new char[longestFinalGradeLength - record.getFinalGrade().length()]).replace("\0", " "));
            sb.append(" |\n");
        }

        sb.append("+");
        sb.append(new String(new char[longestJmbagLength + 2]).replace("\0", "="));
        sb.append("+");
        sb.append(new String(new char[longestLastNameLength + 2]).replace("\0", "="));
        sb.append("+");
        sb.append(new String(new char[longestFirstNameLength + 2]).replace("\0", "="));
        sb.append("+");
        sb.append(new String(new char[longestFinalGradeLength + 2]).replace("\0", "="));
        sb.append("+");

        return sb.toString();


    }
}
