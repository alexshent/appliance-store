package ua.com.alevel.alexshent.service;

import org.junit.jupiter.api.Test;
import ua.com.alevel.alexshent.model.Customer;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    @Test
    void createRandomCustomer_twoRandomCustomersAreNotEqual() {
        PersonService personService = new PersonService();
        Customer customer1 = personService.createRandomCustomer();
        Customer customer2 = personService.createRandomCustomer();
        assertNotEquals(customer1, customer2);
    }
}