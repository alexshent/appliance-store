package ua.com.alevel.alexshent.service;

import ua.com.alevel.alexshent.model.Customer;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

/**
 * Person services provider
 */
public class PersonService {
    private final Random random = new Random();
    private static final int EMAIL_LENGTH = 5;

    public Customer createRandomCustomer() {
        Customer customer = new Customer();
        String randomId = UUID.randomUUID().toString();
        String randomEmail = generateRandomAlphanumericString(EMAIL_LENGTH) +
                '@' +
                generateRandomAlphanumericString(EMAIL_LENGTH) +
                ".com";
        int randomYear = random.nextInt(10) + 2000;
        int randomMonth = random.nextInt(12) + 1;
        int randomDay = random.nextInt(28) + 1;
        LocalDate randomDate = LocalDate.of(randomYear, randomMonth, randomDay);
        customer.setId(randomId);
        customer.setEmail(randomEmail);
        customer.setBirthdate(randomDate);
        return customer;
    }

    private String generateRandomAlphanumericString(int length) {
        final char leftLimitNumeric = '0';
        final char rightLimitNumeric = '9';
        final char leftLimitLetterCapital = 'A';
        final char rightLimitLetterCapital = 'Z';
        final char leftLimitLetterSmall = 'a';
        final char rightLimitLetterSmall = 'z';
        return random.ints(leftLimitNumeric, rightLimitLetterSmall + 1)
                .filter(i -> (
                        (i >= leftLimitNumeric && i <= rightLimitNumeric) ||
                        (i >= leftLimitLetterCapital && i <= rightLimitLetterCapital) ||
                        (i >= leftLimitLetterSmall && i <= rightLimitLetterSmall)
                ))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
