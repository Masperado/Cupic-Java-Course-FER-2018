package hr.fer.zemris.java.hw15.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

/**
 * This class represents model for blog comment.
 */
@Entity
@Table(name = "blog_comments")
@Cacheable()
public class BlogComment {

    /**
     * Id of comment.
     */
    private Long id;

    /**
     * Entry where comment belongs.
     */
    private BlogEntry blogEntry;

    /**
     * Email of user that left the comment.
     */
    private String usersEMail;

    /**
     * Comment message.
     */
    private String message;

    /**
     * Date when comment was posted.
     */
    private Date postedOn;

    /**
     * Getter for id.
     *
     * @return Id
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    /**
     * Getter for blog entry.
     *
     * @return {@link BlogEntry}
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }


    /**
     * Getter for email.
     *
     * @return Email
     */
    @Column(length = 200, nullable = false)
    public String getUsersEMail() {
        return usersEMail;
    }


    /**
     * Getter for message.
     *
     * @return Message
     */
    @Column(length = 4096, nullable = false)
    public String getMessage() {
        return message;
    }


    /**
     * Getter for posted on date.
     *
     * @return Posted on date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getPostedOn() {
        return postedOn;
    }


    /**
     * Setter for id.
     *
     * @param id Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Setter for blog entry.
     *
     * @param blogEntry Blog entry
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Setter for user email.
     *
     * @param usersEMail User email
     */
    public void setUsersEMail(String usersEMail) {
        this.usersEMail = usersEMail;
    }

    /**
     * Setter for message.
     *
     * @param message Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter for posted on date.
     *
     * @param postedOn Posted on date
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogComment that = (BlogComment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}