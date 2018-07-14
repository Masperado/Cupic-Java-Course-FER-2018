package hr.fer.zemris.java.hw16;


import java.io.IOException;
import java.nio.file.Path;

import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This class represents document. It is used to store path to .txt file to disk and its tf vector.
 */
public class Document {

    /**
     * Path to document.
     */
    private Path path;

    /**
     * Map of tf vector values.
     */
    private Map<String, Integer> tf = new LinkedHashMap<>();

    /**
     * Tf-idf vector of document.
     */
    private Vector tfIdf;

    /**
     * Similarity with search document.
     */
    private double similarity;

    /**
     * Constructor with path to document and stopwords.
     *
     * @param path      Path to document
     * @param stopWords List of stopwords
     * @throws IOException IOException
     */
    public Document(Path path, List<String> stopWords) throws IOException {
        this.path = path;

        load(Files.readAllLines(path), stopWords);
    }

    /**
     * Constructor with list of lines and stopwords.
     *
     * @param lines     List of lines
     * @param stopWords List of stopwords
     */
    public Document(List<String> lines, List<String> stopWords) {
        load(lines, stopWords);
    }

    /**
     * This method is used to load tf vector.
     *
     * @param lines     List of lines
     * @param stopWords List of stopwords
     */
    private void load(List<String> lines, List<String> stopWords) {

        for (String line : lines) {
            Pattern pattern = Pattern.compile("[\\W]|[\\d]", Pattern.UNICODE_CHARACTER_CLASS);
            String[] words = pattern.split(line);

            for (String word : words) {
                word = word.toLowerCase();
                if (word.equals("") || stopWords.contains(word)) continue;
                Integer count = tf.get(word);
                if (count == null) {
                    count = 0;
                }
                tf.put(word, count + 1);
            }
        }
    }

    /**
     * Getter for word set.
     *
     * @return Word set
     */
    public Set<String> getWords() {
        return tf.keySet();
    }

    /**
     * Getter for tf value for given word.
     *
     * @param word Word
     * @return Tf value
     */
    public Integer getTf(String word) {
        return tf.get(word);
    }

    /**
     * Getter for tf-idf vector.
     *
     * @return Tf-idf vector
     */
    public Vector getTfIdf() {
        return tfIdf;
    }

    /**
     * Setter for tf-idf vector.
     *
     * @param tfIdf Tf-idf vector
     */
    public void setTfIdf(Vector tfIdf) {
        this.tfIdf = tfIdf;
    }

    /**
     * Getter for cosine similarity.
     *
     * @return Cosine similarity
     */
    public double getSimilarity() {
        return similarity;
    }

    /**
     * Setter for cosine similarity.
     *
     * @param similarity Similarity
     */
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    /**
     * Getter for path to document.
     *
     * @return Path to document
     */
    public Path getPath() {
        return path;
    }

    /**
     * Getter for lines.
     *
     * @return lines
     */
    public List<String> getLines() {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Pogreška u čitanju iz datoteke " + path);
            System.exit(1);
        }
        return null;
    }
}
