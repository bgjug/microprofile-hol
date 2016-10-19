package bg.jug.authors.domain;

/**
 * Created by Dmitry Alexandrov on 19.10.16.
 */
public class Author {

    private String firstName;
    private String lastName;
    private String email;
    private boolean isRegular;
    private int salary;

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Is regular boolean.
     *
     * @return the boolean
     */
    public boolean isRegular() {
        return isRegular;
    }

    /**
     * Sets regular.
     *
     * @param regular the regular
     */
    public void setRegular(boolean regular) {
        isRegular = regular;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Sets salary.
     *
     * @param salary the salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (isRegular != author.isRegular) return false;
        if (salary != author.salary) return false;
        if (!firstName.equals(author.firstName)) return false;
        if (!lastName.equals(author.lastName)) return false;
        return email.equals(author.email);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (isRegular ? 1 : 0);
        result = 31 * result + salary;
        return result;
    }
}
