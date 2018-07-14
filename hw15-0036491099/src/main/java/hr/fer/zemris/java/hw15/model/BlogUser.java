package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This class represent model for blog user.
 */
@Entity
@Table(name = "blog_users")
@Cacheable()
public class BlogUser {

    /**
     * Id of user.
     */
    private Long id;

    /**
     * User first name.
     */
    private String firstName;

    /**
     * User last name.
     */
    private String lastName;

    /**
     * User nickname.
     */
    private String nick;

    /**
     * User email.
     */
    private String email;

    /**
     * User password hash.
     */
    private String passwordHash;

    /**
     * User blog entries.
     */
    private List<BlogEntry> entries;

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
     * Getter for first name.
     *
     * @return First name
     */
    @Column(length = 200, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for last name.
     *
     * @return Last name
     */
    @Column(length = 200, nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for nickname.
     *
     * @return Nickname
     */
    @Column(length = 200, nullable = false, unique = true)
    public String getNick() {
        return nick;
    }

    /**
     * Getter for email.
     *
     * @return Email
     */
    @Column(length = 200, nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * Getter for password hash.
     *
     * @return Password hash
     */
    @Column(length = 200, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Getter for user entries.
     *
     * @return Entries
     */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    public List<BlogEntry> getEntries() {
        return entries;
    }

    /**
     * Setter for id.
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Setter for first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for last name.
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for nickname.
     *
     * @param nick Nickname
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Setter for email.
     *
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for password hash.
     *
     * @param passwordHash Password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Setter for entries.
     *
     * @param entries Entries
     */
    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogUser blogUser = (BlogUser) o;
        return Objects.equals(id, blogUser.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
