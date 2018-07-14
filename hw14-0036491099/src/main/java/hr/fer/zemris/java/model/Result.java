package hr.fer.zemris.java.model;

/**
 * This class represents model for result of voting.
 */
public class Result {

    /**
     * Song votes.
     */
    private String result;

    /**
     * Result song.
     */
    private Song song;

    /**
     * Basic constructor.
     *
     * @param id     Song id
     * @param name   Song name
     * @param link   Song link
     * @param result Song result
     */
    public Result(String id, String name, String link, String result) {
        this.song = new Song(id, name, link);
        this.result = result;
    }

    /**
     * Getter for id.
     *
     * @return id
     */
    public String getId() {
        return song.getId();
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public String getName() {
        return song.getName();
    }

    /**
     * Getter for link.
     *
     * @return link
     */
    public String getLink() {
        return song.getLink();
    }

    /**
     * Getter for result.
     *
     * @return result
     */
    public String getResult() {
        return result;
    }
}