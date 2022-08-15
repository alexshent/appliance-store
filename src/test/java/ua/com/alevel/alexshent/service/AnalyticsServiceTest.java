package ua.com.alevel.alexshent.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.alexshent.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyticsServiceTest {
    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        analyticsService = new AnalyticsService();
    }

    // ===========================================

    @Test
    void calculateAllInvoicesTotal_mockInvoices() {
        BigDecimal invoice1Total = BigDecimal.valueOf(111.11);
        BigDecimal invoice2Total = BigDecimal.valueOf(222.22);
        BigDecimal invoice3Total = BigDecimal.valueOf(333.33);
        BigDecimal expected = BigDecimal.valueOf(666.66);

        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.calculateTotal()).thenReturn(invoice1Total);
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.calculateTotal()).thenReturn(invoice2Total);
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.calculateTotal()).thenReturn(invoice3Total);
        List<Invoice> list = new ArrayList<>();
        list.add(invoice1);
        list.add(invoice2);
        list.add(invoice3);
        BigDecimal actual = analyticsService.calculateAllInvoicesTotal(list);
        assertEquals(expected, actual);
    }

    @Test
    void calculateAllInvoicesTotal_noInvoices() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.calculateAllInvoicesTotal(null);
        });
    }

    // ===========================================

    @Test
    void getNumberOfAppliancesByType_mockInvoices() {
        Map<String, Long> expected = new HashMap<>();
        expected.put("Telephone", 6L);
        expected.put("Television", 3L);
        // -------------------------------------
        Appliance appliance11 = mock(Telephone.class);
        when(appliance11.getType()).thenReturn("Telephone");
        Appliance appliance12 = mock(Telephone.class);
        when(appliance12.getType()).thenReturn("Telephone");
        Appliance appliance13 = mock(Television.class);
        when(appliance13.getType()).thenReturn("Television");
        List<Appliance> list1 = new ArrayList<>();
        list1.add(appliance11);
        list1.add(appliance12);
        list1.add(appliance13);
        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.getList()).thenReturn(list1);
        // -------------------------------------
        Appliance appliance21 = mock(Telephone.class);
        when(appliance21.getType()).thenReturn("Telephone");
        Appliance appliance22 = mock(Telephone.class);
        when(appliance22.getType()).thenReturn("Telephone");
        Appliance appliance23 = mock(Television.class);
        when(appliance23.getType()).thenReturn("Television");
        List<Appliance> list2 = new ArrayList<>();
        list2.add(appliance21);
        list2.add(appliance22);
        list2.add(appliance23);
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.getList()).thenReturn(list2);
        // -------------------------------------
        Appliance appliance31 = mock(Telephone.class);
        when(appliance31.getType()).thenReturn("Telephone");
        Appliance appliance32 = mock(Telephone.class);
        when(appliance32.getType()).thenReturn("Telephone");
        Appliance appliance33 = mock(Television.class);
        when(appliance33.getType()).thenReturn("Television");
        List<Appliance> list3 = new ArrayList<>();
        list1.add(appliance31);
        list1.add(appliance32);
        list1.add(appliance33);
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.getList()).thenReturn(list3);
        // -------------------------------------
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        // -------------------------------------
        Map<String, Long> actual = analyticsService.getNumberOfAppliancesByType(invoices);
        assertEquals(expected, actual);
    }

    @Test
    void getNumberOfAppliancesByType_noInvoices() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getNumberOfAppliancesByType(null);
        });
    }

    // ===========================================

    @Test
    void getNumberOfInvoicesByType_mockInvoices() {
        Map<InvoiceType, Long> expected = new HashMap<>();
        expected.put(InvoiceType.WHOLESALE, 2L);
        expected.put(InvoiceType.RETAIL, 1L);
        // -------------------------------------
        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.getType()).thenReturn(InvoiceType.WHOLESALE);
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.getType()).thenReturn(InvoiceType.WHOLESALE);
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.getType()).thenReturn(InvoiceType.RETAIL);
        // -------------------------------------
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        // -------------------------------------
        Map<InvoiceType, Long> actual = analyticsService.getNumberOfInvoicesByType(invoices);
        assertEquals(expected, actual);
    }

    @Test
    void getNumberOfInvoicesByType_noInvoices() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getNumberOfInvoicesByType(null);
        });
    }

    // ===========================================

    @Test
    void getNumberOfRetailInvoices_mockInvoices() {
        long expected = 2;
        // -------------------------------------
        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.getType()).thenReturn(InvoiceType.WHOLESALE);
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.getType()).thenReturn(InvoiceType.RETAIL);
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.getType()).thenReturn(InvoiceType.RETAIL);
        // -------------------------------------
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        // -------------------------------------
        long actual = analyticsService.getNumberOfRetailInvoices(invoices);
        assertEquals(expected, actual);
    }

    @Test
    void getNumberOfRetailInvoices_noInvoices() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getNumberOfRetailInvoices(null);
        });
    }

    // ===========================================

    @Test
    void getFirstInvoices_mockInvoices() {
        final int limitNumber = 3;
        LocalDateTime createdAt1 = LocalDateTime.now();
        LocalDateTime createdAt2 = createdAt1.minusHours(1);
        LocalDateTime createdAt3 = createdAt1.minusHours(2);
        LocalDateTime createdAt4 = createdAt1.minusHours(3);
        LocalDateTime createdAt5 = createdAt1.minusHours(4);
        // -------------------------------------
        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.getCreatedAt()).thenReturn(createdAt1);
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.getCreatedAt()).thenReturn(createdAt2);
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.getCreatedAt()).thenReturn(createdAt3);
        Invoice invoice4 = mock(Invoice.class);
        when(invoice4.getCreatedAt()).thenReturn(createdAt4);
        Invoice invoice5 = mock(Invoice.class);
        when(invoice5.getCreatedAt()).thenReturn(createdAt5);
        // -------------------------------------
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice5);
        expected.add(invoice4);
        expected.add(invoice3);
        // -------------------------------------
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);
        // -------------------------------------
        List<Invoice> actual = analyticsService.getFirstInvoices(invoices, limitNumber);
        assertEquals(expected, actual);
    }

    @Test
    void getFirstInvoices_noInvoices() {
        final int limitNumber = 3;
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getFirstInvoices(null, limitNumber);
        });
    }

    // ===========================================

    @Test
    void getLowestCostInvoice_mockInvoices() {
        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.calculateTotal()).thenReturn(BigDecimal.valueOf(1.11));
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.calculateTotal()).thenReturn(BigDecimal.valueOf(2.22));
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.calculateTotal()).thenReturn(BigDecimal.valueOf(3.33));
        // -------------------------------------
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice3);
        invoices.add(invoice2);
        invoices.add(invoice1);
        // -------------------------------------
        Invoice actual = analyticsService.getLowestCostInvoice(invoices);
        assertEquals(invoice1, actual);
    }

    @Test
    void getLowestCostInvoice_noInvoices() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getLowestCostInvoice(null);
        });
    }

    // ===========================================

    @Test
    void getSingleApplianceTypeInvoices_mockInvoices() {
        Appliance appliance11 = mock(Telephone.class);
        when(appliance11.getType()).thenReturn("Telephone");
        Appliance appliance12 = mock(Telephone.class);
        when(appliance12.getType()).thenReturn("Telephone");
        Appliance appliance13 = mock(Television.class);
        when(appliance13.getType()).thenReturn("Television");
        List<Appliance> list1 = new ArrayList<>();
        list1.add(appliance11);
        list1.add(appliance12);
        list1.add(appliance13);
        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.getList()).thenReturn(list1);
        // -------------------------------------
        Appliance appliance21 = mock(Telephone.class);
        when(appliance21.getType()).thenReturn("Telephone");
        Appliance appliance22 = mock(Telephone.class);
        when(appliance22.getType()).thenReturn("Telephone");
        Appliance appliance23 = mock(Television.class);
        when(appliance23.getType()).thenReturn("Telephone");
        List<Appliance> list2 = new ArrayList<>();
        list2.add(appliance21);
        list2.add(appliance22);
        list2.add(appliance23);
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.getList()).thenReturn(list2);
        // -------------------------------------
        Appliance appliance31 = mock(Telephone.class);
        when(appliance31.getType()).thenReturn("Television");
        Appliance appliance32 = mock(Telephone.class);
        when(appliance32.getType()).thenReturn("Television");
        Appliance appliance33 = mock(Television.class);
        when(appliance33.getType()).thenReturn("Television");
        List<Appliance> list3 = new ArrayList<>();
        list3.add(appliance31);
        list3.add(appliance32);
        list3.add(appliance33);
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.getList()).thenReturn(list3);
        // -------------------------------------
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        // -------------------------------------
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice2);
        expected.add(invoice3);
        // -------------------------------------
        List<Invoice> actual = analyticsService.getSingleApplianceTypeInvoices(invoices);
        assertEquals(expected, actual);
    }

    @Test
    void getSingleApplianceTypeInvoices_noInvoices() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getSingleApplianceTypeInvoices(null);
        });
    }

    // ===========================================

    @Test
    void getUnderageInvoices_mockInvoices() {
        LocalDate birthdate1 = LocalDate.parse("2010-10-22");
        LocalDate birthdate2 = LocalDate.parse("2000-10-22");
        LocalDate birthdate3 = LocalDate.parse("2000-10-12");
        // -------------------------------------
        Customer customer1 = mock(Customer.class);
        when(customer1.getBirthdate()).thenReturn(birthdate1);
        Customer customer2 = mock(Customer.class);
        when(customer2.getBirthdate()).thenReturn(birthdate2);
        Customer customer3 = mock(Customer.class);
        when(customer3.getBirthdate()).thenReturn(birthdate3);
        // -------------------------------------
        Invoice invoice1 = mock(Invoice.class);
        when(invoice1.getCustomer()).thenReturn(customer1);
        Invoice invoice2 = mock(Invoice.class);
        when(invoice2.getCustomer()).thenReturn(customer2);
        Invoice invoice3 = mock(Invoice.class);
        when(invoice3.getCustomer()).thenReturn(customer3);
        // -------------------------------------
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        // -------------------------------------
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        // -------------------------------------
        List<Invoice> actual = analyticsService.getUnderageInvoices(invoices);
        assertEquals(expected, actual);
    }

    @Test
    void getUnderageInvoices_noInvoices() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getUnderageInvoices(null);
        });
    }
}