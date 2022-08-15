package ua.com.alevel.alexshent.model;

import ua.com.alevel.alexshent.Configuration;

import java.math.BigDecimal;

/**
 * Invoice type
 *
 * WHOLESALE - if invoice total > INVOICE_TOTAL_LIMIT
 * RETAIL - if invoice total <= INVOICE_TOTAL_LIMIT
 * UNDERAGE - if customers age < 18
 */
public enum InvoiceType {
    WHOLESALE, RETAIL, UNDERAGE;

    public static InvoiceType getTypeForTotal(BigDecimal total) {
        String invoiceTotalLimitString = Configuration.getInstance().getProperty("INVOICE_TOTAL_LIMIT");
        BigDecimal invoiceTotalLimit = BigDecimal.valueOf(Double.parseDouble(invoiceTotalLimitString));
        if (total.compareTo(invoiceTotalLimit) > 0) {
            return WHOLESALE;
        }
        return RETAIL;
    }
}
