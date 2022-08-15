package ua.com.alevel.alexshent.service;

import ua.com.alevel.alexshent.model.Appliance;
import ua.com.alevel.alexshent.model.Invoice;
import ua.com.alevel.alexshent.model.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Analytics services provider
 */
public class AnalyticsService {
    private static final String INVOICE_LIST_IS_NULL = "invoice list is null";

    /**
     * All invoices total
     * @param invoices input list of invoices
     * @return total from all invoices
     */
    public BigDecimal calculateAllInvoicesTotal(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        return
                invoices.stream()
                        .map(Invoice::calculateTotal)
                        .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
    }

    /**
     * Number of appliances by type (Telephone/Television)
     * @param invoices input list of invoices
     * @return map where key is appliance type and value is appliance counter
     */
    public Map<String, Long> getNumberOfAppliancesByType(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        return
                invoices.stream()
                        .flatMap(invoice -> invoice.getList().stream())
                        .collect(Collectors.groupingBy(Appliance::getType, Collectors.counting()));
    }

    /**
     * Number of invoices by type (Wholesale/Retail)
     * @param invoices input list of invoices
     * @return map where key is invoice type and value is invoice counter
     */
    public Map<InvoiceType, Long> getNumberOfInvoicesByType(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        return
                invoices.stream()
                        .collect(Collectors.groupingBy(Invoice::getType, Collectors.counting()));
    }

    /**
     * Number of retail invoices
     * @param invoices input list of invoices
     * @return number of invoices of retail type
     */
    public long getNumberOfRetailInvoices(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        return
                invoices.stream()
                        .filter(invoice -> invoice.getType() == InvoiceType.RETAIL)
                        .count();
    }

    /**
     * First invoices
     * @param invoices input list of invoices
     * @param limitNumber number of invoices
     * @return list of the first invoices
     */
    public List<Invoice> getFirstInvoices(List<Invoice> invoices, int limitNumber) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        return
                invoices.stream()
                        .sorted(Comparator.comparing(Invoice::getCreatedAt))
                        .limit(limitNumber)
                        .toList();
    }

    /**
     * Lowest cost invoice
     * @param invoices input list of invoices
     * @return invoice with the lowest cost
     */
    public Invoice getLowestCostInvoice(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        return
                invoices.stream()
                        .sorted(Comparator.comparing(Invoice::calculateTotal))
                        .limit(1)
                        .toList()
                        .get(0);
    }

    /**
     * Single appliance type invoices
     * @param invoices input list of invoices
     * @return list of invoices with one type of appliances only
     */
    public List<Invoice> getSingleApplianceTypeInvoices(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        return
                invoices.stream()
                        .filter(
                                invoice -> invoice.getList().stream().collect(
                                        Collectors.groupingBy(Appliance::getType, Collectors.counting())
                                ).size() == 1
                        )
                        .toList();
    }

    /**
     * Underage invoices
     * @param invoices input list of invoices
     * @return list of underage invoices
     */
    public List<Invoice> getUnderageInvoices(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }
        List<Invoice> underageInvoices =
                invoices.stream()
                        .filter(invoice -> Period.between(invoice.getCustomer().getBirthdate(), LocalDate.now()).getYears() < 18)
                        .toList();
        underageInvoices.forEach(invoice -> invoice.setType(InvoiceType.UNDERAGE));
        return underageInvoices;
    }
}
