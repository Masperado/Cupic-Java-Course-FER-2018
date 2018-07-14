package hr.fer.zemris.java.hw17;

/**
 * This class represents data model for picture.
 */
public class Picture {

    /**
     * Path to picture.
     */
    private String path;

    /**
     * Name of picture.
     */
    private String name;

    /**
     * Picture tags.
     */
    private String[] tags;

    /**
     * Basic constructor.
     *
     * @param path Path to picture
     * @param name Name of picture
     * @param tags Picture tags
     */
    public Picture(String path, String name, String[] tags) {
        this.path = path;
        this.name = name;
        this.tags = tags;
    }

    /**
     * Getter for path.
     *
     * @return Path
     */
    public String getPath() {
        return path;
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
     * Getter for tags.
     *
     * @return Tags
     */
    public String[] getTags() {
        return tags;
    }

}
