package abstractions;

/**
 * Blueprint for a contact
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /**
     * Contact constructor
     * @param contactId
     * @param contactName
     * @param email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }
}
