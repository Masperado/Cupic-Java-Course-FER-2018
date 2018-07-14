package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * This class represents model for blog entry.
 */
@Entity
@Table(name = "blog_entries")
@Cacheable()
public class BlogEntry {

    /**
     * Id of entry.
     */
    private Long id;

    /**
     * List of comments.
     */
    private List<BlogComment> comments = new ArrayList<>();

    /**
     * Created date.
     */
    private Date createdAt;

    /**
     * Last modified date.
     */
    private Date lastModifiedAt;

    /**
     * Title of entry.
     */
    private String title;

    /**
     * Text of entry.
     */
    private String text;

    /**
     * Creator of entry.
     */
    private BlogUser creator;

    /**
     * Getter for id.
     *
     * @return ID
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    /**
     * Getter for comments.
     *
     * @return Comments
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    public List<BlogComment> getComments() {
        return comments;
    }


    /**
     * Getter for created date.
     *
     * @return Created date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }


    /**
     * Getter for last modified date.
     *
     * @return Last modified date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }


    /**
     * Getter for title.
     *
     * @return Title
     */
    @Column(length = 200, nullable = false)
    public String getTitle() {
        return title;
    }


    /**
     * Getter for text.
     *
     * @return Text
     */
    @Column(length = 4096, nullable = false)
    public String getText() {
        return text;
    }

    /**
     * Getter for creator.
     *
     * @return Creator
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Setter for id.
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Setter for comments.
     *
     * @param comments Comments
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Setter for created at date.
     *
     * @param createdAt Created date
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Setter for last modified date.
     *
     * @param lastModifiedAt Last modified date
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Setter for title.
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for text.
     *
     * @param text Text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Setter for creator.
     *
     * @param creator Creator
     */
    public void setCreator(BlogUser creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogEntry blogEntry = (BlogEntry) o;
        return Objects.equals(id, blogEntry.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}