package base.model;

/**
 * This class contains all details regarding the deserialization of User elements from API
 *
 */
public class User {

    /**
     * Elements contained by User
     */
    private String user;
    private String email;
    private boolean subscription;
    private int id;

    public User() {
    }

    //Setters and Getters generated

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
