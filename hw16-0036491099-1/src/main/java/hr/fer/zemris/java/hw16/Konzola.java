package hr.fer.zemris.java.hw16;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * This class represents console program for searching through articles based on keywords.
 */
public class Konzola {

    /**
     * Vocabulary set.
     */
    private static Set<String> vokabular = new HashSet<>();

    /**
     * Idf vector of articles.
     */
    private static Map<String, Double> idfVector = new HashMap<>();

    /**
     * List of all articles.
     */
    private static List<Document> documents = new ArrayList<>();

    /**
     * List of stopwords.
     */
    private static List<String> stopWords;

    /**
     * List of results.
     */
    private static List<Document> results;

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Molim proslijedite putanju do članaka!");
            System.exit(1);
        }

        try {
            loadDocumentsAndStopWords(args[0]);
        } catch (IOException e) {
            System.out.println("Pogreška pri učitavanju članaka!");
            System.exit(1);
        }

        loadVocabularAndTfIDFVector();


        Scanner sc = new Scanner(System.in);

        System.out.println("Veličina riječnika je " + vokabular.size() + " riječi.");

        while (true) {
            boolean exit = false;
            System.out.print("Enter command > ");
            String[] input = sc.nextLine().split("\\s+");

            if (input.length == 0) {
                System.out.println("Nepoznata naredba.");
                continue;
            }
            switch (input[0]) {
                case "query":
                    parseQuery(input);
                    break;
                case "type":
                    if (results == null) {
                        System.out.println("Prvo napravite query.");
                        continue;
                    }
                    if (input.length != 2) {
                        System.out.println("Nepoznata naredba.");
                        continue;
                    }
                    try {
                        Document doc = results.get(Integer.parseInt(input[1]));
                        System.out.println("----------------------------------------------------------------");
                        for (String line : doc.getLines()) {
                            System.out.println(line);
                        }
                        System.out.println("----------------------------------------------------------------");
                        System.out.println();
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        System.out.println("Krivi broj kod type naredbe.");
                        continue;
                    }
                    break;
                case "results":
                    if (results == null) {
                        System.out.println("Prvo napravite query.");
                        continue;
                    }
                    if (input.length != 1) {
                        System.out.println("Nepoznata naredba.");
                        continue;
                    }

                    for (int i = 0; i < results.size(); i++) {
                        System.out.printf("[ %d] (%.4f) %s\r\n", i, results.get(i).getSimilarity(), results.get(i).getPath());
                    }
                    System.out.println();
                    break;
                case "exit":
                    if (input.length != 1) {
                        System.out.println("Nepoznata naredba.");
                        continue;
                    }
                    exit = true;
                    break;
                default:
                    System.out.println("Nepoznata naredba.");
                    break;
            }

            if (exit) {
                break;
            }


        }

        sc.close();


    }

    /**
     * This method is used for parsing query.
     *
     * @param input Array of keywords
     */
    private static void parseQuery(String[] input) {
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            if (i == 0) continue;
            lines.add(input[i]);
        }


        Document searchDocument = new Document(lines, stopWords);

        results = getResults(searchDocument);

        System.out.println("Query is: " + searchDocument.getWords());
        System.out.println("Najboljih 10 rezultata:");
        for (int i = 0; i < results.size(); i++) {
            System.out.printf("[ %d] (%.4f) %s\r\n", i, results.get(i).getSimilarity(), results.get(i).getPath());
        }
        System.out.println();

    }

    /**
     * This method is used for getting most similar articles for given search document.
     *
     * @param searchDocument Search document
     * @return List of documents
     */
    private static List<Document> getResults(Document searchDocument) {

        Vector tfIdf = new Vector();
        for (String word : vokabular) {
            Integer tf = searchDocument.getTf(word);
            if (tf == null) tf = 0;
            Double idf = idfVector.get(word);
            tfIdf.addValue(tf * idf);
        }
        searchDocument.setTfIdf(tfIdf);

        List<Document> results = new ArrayList<>();

        for (Document doc : documents) {
            double similarity = doc.getTfIdf().cosineSimilarity(searchDocument.getTfIdf());
            if (similarity > 0) {
                doc.setSimilarity(similarity);
                results.add(doc);
            }
        }

        results.sort((o, v) -> (Double.compare(v.getSimilarity(), o.getSimilarity())));


        List<Document> topResults = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if (i == results.size()) {
                break;
            }
            topResults.add(results.get(i));
        }


        return topResults;
    }

    /**
     * This method is used for loading vocabular and loading tf-idf vector.
     */
    private static void loadVocabularAndTfIDFVector() {
        for (Document doc : documents) {
            vokabular.addAll(doc.getWords());
        }

        for (String word : vokabular) {
            int occurences = 0;

            for (Document doc : documents) {
                if (doc.getTf(word) != null) {
                    occurences++;
                }
            }

            idfVector.put(word, Math.log((double) documents.size() / occurences));
        }

        for (Document doc : documents) {
            Vector tfIdf = new Vector();
            for (String word : vokabular) {
                Integer tf = doc.getTf(word);
                if (tf == null) {
                    tf = 0;
                }
                Double idf = idfVector.get(word);
                tfIdf.addValue(tf * idf);
            }
            doc.setTfIdf(tfIdf);
        }
    }

    /**
     * This method is used for loading documents and stopwords.
     *
     * @param path Path to documents
     * @throws IOException Input output exception
     */
    private static void loadDocumentsAndStopWords(String path) throws IOException {

        if (!Files.isDirectory(Paths.get(path))) {
            throw new IOException("Putanja nije datoteka!");
        }

        stopWords = Files.readAllLines(Paths.get("stoprijeci.txt"));


        Files.walkFileTree(Paths.get(path), new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                documents.add(new Document(file, stopWords));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

    }


}
