package ua.com.alevel.alexshent.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Customer class
 */
public class Customer {
    private String id;
    private String email;
    private LocalDate birthdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Customer customer) {
            return this.getEmail().equals(customer.getEmail())
                    && this.getBirthdate().equals(customer.getBirthdate());
        }
        return false;
    }

    @Override
    public String toString() {
        return
                """
                        Customer {
                        id = %s,
                        email = %s,
                        birthdate = %s
                        }""".formatted(id, email, birthdate.toString());
    }
}
