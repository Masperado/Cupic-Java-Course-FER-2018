package hr.fer.zemris.java.model;

/**
 * This class represents poll.
 */
public class Poll {

    /**
     * Id of poll.
     */
    public String id;

    /**
     * Title of poll.
     */
    public String title;

    /**
     * Message of poll.
     */
    public String message;

    /**
     * Basic constructor.
     *
     * @param id      Id
     * @param title   Title
     * @param message Message
     */
    public Poll(String id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
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
     * Getter for title.
     *
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for message.
     *
     * @return Message
     */
    public String getMessage() {
        return message;
    }
}
