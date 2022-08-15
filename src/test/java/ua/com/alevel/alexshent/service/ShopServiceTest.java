package ua.com.alevel.alexshent.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.alexshent.exception.InvalidCsvFileException;
import ua.com.alevel.alexshent.exception.NoAppliancesException;
import ua.com.alevel.alexshent.model.Invoice;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopService();
    }

    @Test
    void readAppliancesFromCsvFile_validFile() {
        final String fileName = "stocklist.csv";

        int size = shopService.readAppliancesFromCsvFile(fileName);
        assertTrue(size > 0);
    }

    @Test
    void readAppliancesFromCsvFile_invalidLine() {
        final String fileName = "stocklist_test_invalid_line.csv";

        assertThrows(InvalidCsvFileException.class, () -> {
            shopService.readAppliancesFromCsvFile(fileName);
        });
    }

    @Test
    void generateInvoice_notEmptyList() {
        final String fileName = "stocklist.csv";
        final int maxItems = 5;

        int size = shopService.readAppliancesFromCsvFile(fileName);
        assertTrue(size > 0);
        Invoice invoice = shopService.generateInvoice(maxItems);
        assertNotNull(invoice);
    }

    @Test
    void generateInvoice_emptyList() {
        final int maxItems = 5;

        assertThrows(NoAppliancesException.class, () -> {
            shopService.generateInvoice(maxItems);
        });
    }
}