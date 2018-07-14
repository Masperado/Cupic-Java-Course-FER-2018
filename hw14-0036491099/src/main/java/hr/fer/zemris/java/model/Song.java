package hr.fer.zemris.java.model;

/**
 * This class represents song used in web application poll.
 */
public class Song {

    /**
     * Song id.
     */
    private String id;

    /**
     * Song name.
     */
    private String name;

    /**
     * Song link.
     */
    private String link;

    /**
     * Basic constructor.
     *
     * @param id   Song id
     * @param name Song name
     * @param link Song link
     */
    public Song(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    /**
     * Getter for id.
     *
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for name.
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for link.
     *
     * @return Link
     */
    public String getLink() {
        return link;
    }
}