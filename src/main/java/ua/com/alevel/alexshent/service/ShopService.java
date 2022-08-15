package ua.com.alevel.alexshent.service;

import ua.com.alevel.alexshent.FileParser;
import ua.com.alevel.alexshent.FileReader;
import ua.com.alevel.alexshent.exception.InvalidCsvFileException;
import ua.com.alevel.alexshent.exception.InvalidCsvLineException;
import ua.com.alevel.alexshent.exception.NoAppliancesException;
import ua.com.alevel.alexshent.model.Appliance;
import ua.com.alevel.alexshent.model.ApplianceFactory;
import ua.com.alevel.alexshent.model.Invoice;
import ua.com.alevel.alexshent.model.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Shop services provider
 */
public class ShopService {
    private final Random random = new Random();
    private final List<Appliance> appliances = new ArrayList<>();

    /**
     * Read appliances from CSV file
     * @param fileName resource CSV file name
     */
    public int readAppliancesFromCsvFile(String fileName) {
        FileReader fileReader = new FileReader();
        FileParser fileParser = new FileParser();
        List<String> strings = fileReader.readResourceFile(fileName);
        List<Map<String, String>> columns = null;
        try {
            columns = fileParser.parseCsv(strings);
        } catch (InvalidCsvLineException e) {
            // fail first, consider all CSV file invalid if one line is invalid
            throw new InvalidCsvFileException(fileName + " " + e.getMessage());
        }
        for (Map<String, String> applianceColumns : columns) {
            Appliance appliance = ApplianceFactory.create(applianceColumns);
            appliances.add(appliance);
        }
        return appliances.size();
    }

    /**
     * Generate random invoice with 1-maxItems items
     * @param maxItems maximum number of items in the invoice
     * @return generated invoice
     */
    public Invoice generateInvoice(int maxItems) {
        if (maxItems <= 0) {
            throw new IllegalArgumentException("maxItems <= 0");
        }
        if (appliances.isEmpty()) {
            throw new NoAppliancesException("appliances list is empty");
        }
        PersonService personService = new PersonService();
        List<Appliance> selectedAppliances = new ArrayList<>();
        int nItems = random.nextInt(maxItems) + 1;
        BigDecimal total = BigDecimal.valueOf(0.00);
        for (int i=0; i < nItems; i++) {
            int randomIndex = random.nextInt(appliances.size());
            Appliance randomAppliance = appliances.get(randomIndex);
            selectedAppliances.add(randomAppliance);
            total = total.add(randomAppliance.getPrice());
        }
        Invoice invoice = new Invoice();
        invoice.setList(selectedAppliances);
        invoice.setCustomer(personService.createRandomCustomer());
        invoice.setType(InvoiceType.getTypeForTotal(total));
        invoice.setCreatedAt(LocalDateTime.now());
        return invoice;
    }
}
