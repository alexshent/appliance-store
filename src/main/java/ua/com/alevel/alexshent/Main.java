package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.exception.CreateLogFileException;
import ua.com.alevel.alexshent.exception.ReadLoggingPropertiesException;
import ua.com.alevel.alexshent.model.Invoice;
import ua.com.alevel.alexshent.model.InvoiceType;
import ua.com.alevel.alexshent.service.AnalyticsService;
import ua.com.alevel.alexshent.service.ShopService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String LOGGING_PROPERTIES_FILE_NAME = "logging.properties";
    private static final String INVOICE_LIST_IS_NULL = "invoice list is null";

    public static void main(String[] args) {
        Main main = new Main();
        main.demo();
    }

    /**
     * Print analytics
     * @param invoices input list of invoices
     */
    private void printAnalytics(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }

        AnalyticsService analyticsService = new AnalyticsService();

        // number of appliances by type
        Map<String, Long> appliancesNumberByType = analyticsService.getNumberOfAppliancesByType(invoices);
        logger.log(Level.INFO, () -> "number of appliances by type = " + appliancesNumberByType.toString() + "\n");

        // number of invoices by type
        Map<InvoiceType, Long> invoicesNumberByType = analyticsService.getNumberOfInvoicesByType(invoices);
        logger.log(Level.INFO, () -> "number of invoices by type = " + invoicesNumberByType.toString() + "\n");

        // lowest cost invoice
        Invoice lowestCostInvoice = analyticsService.getLowestCostInvoice(invoices);
        logger.log(Level.INFO, () -> "lowest cost invoice total = " + lowestCostInvoice.calculateTotal()
                + "\n"
                + lowestCostInvoice.getCustomer().toString() + "\n");

        // all invoices total
        BigDecimal allInvoicesTotal = analyticsService.calculateAllInvoicesTotal(invoices);
        logger.log(Level.INFO, () -> "all invoices total = " + allInvoicesTotal.toString() + "\n");

        // number of retail invoices
        long numberORetailInvoices = analyticsService.getNumberOfRetailInvoices(invoices);
        logger.log(Level.INFO, () -> "number of retail invoices = " + numberORetailInvoices + "\n");

        // single appliance type invoices
        List<Invoice> singleApplianceTypeInvoices = analyticsService.getSingleApplianceTypeInvoices(invoices);
        logger.log(Level.INFO, () -> "single appliance type invoices = " + singleApplianceTypeInvoices + "\n");

        // first invoices
        String firstInvoicesNumberLimitString = Configuration.getInstance().getProperty("FIRST_INVOICES_NUMBER_LIMIT");
        int firstInvoicesNumberLimit = Integer.parseInt(firstInvoicesNumberLimitString);
        List<Invoice> firstInvoices = analyticsService.getFirstInvoices(invoices, firstInvoicesNumberLimit);
        logger.log(Level.INFO, () -> "first invoices = " + firstInvoices.toString() + "\n");

        // underage invoices
        List<Invoice> underageInvoices = analyticsService.getUnderageInvoices(invoices);
        logger.log(Level.INFO, () -> "underage invoices = " + underageInvoices + "\n");
    }

    /**
     * Sort invoices:
     * at first by customers age descending (customer birthdate ascending)
     * then by number of appliances ascending
     * then by invoice total ascending
     * @param invoices input list of invoices
     */
    private void sortInvoices(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException(INVOICE_LIST_IS_NULL);
        }

        class InvoiceComparatorCustomerBirthdateAsc<T extends Invoice> implements Comparator<T> {
            @Override
            public int compare(T first, T second) {
                // customer birthdate, asc
                return first.getCustomer().getBirthdate().compareTo(second.getCustomer().getBirthdate());
            }
        }

        class InvoiceComparatorAppliancesNumberAsc<T extends Invoice> implements Comparator<T> {
            @Override
            public int compare(T first, T second) {
                // appliances number, asc
                Integer firstNumber = first.getList().size();
                Integer secondNumber = second.getList().size();
                return firstNumber.compareTo(secondNumber);
            }
        }

        class InvoiceComparatorTotalAsc<T extends Invoice> implements Comparator<T> {
            @Override
            public int compare(T first, T second) {
                // invoice total, asc
                return first.calculateTotal().compareTo(second.calculateTotal());
            }
        }

        Comparator<Invoice> comparator = new InvoiceComparatorCustomerBirthdateAsc<>()
                .thenComparing(new InvoiceComparatorAppliancesNumberAsc<>())
                .thenComparing(new InvoiceComparatorTotalAsc<>());

        invoices.sort(comparator);
        logger.log(Level.INFO, () -> "sorted invoices = " + invoices + "\n");
    }

    public void demo() {
        try {
            LogManager.getLogManager().readConfiguration(getClass().getClassLoader().getResourceAsStream(LOGGING_PROPERTIES_FILE_NAME));
        } catch (IOException e) {
            throw new ReadLoggingPropertiesException("logging.properties file input stream is null");
        }
        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false);         // disable default console handler
        List<Invoice> invoices = new ArrayList<>();
        ShopService shopService = new ShopService();
        String stockListFileString = Configuration.getInstance().getProperty("STOCK_LIST");
        shopService.readAppliancesFromCsvFile(stockListFileString);
        String invoicesNumberString = Configuration.getInstance().getProperty("INVOICES_NUMBER");
        int invoicesNumber = Integer.parseInt(invoicesNumberString);
        // create invoices and save details to log files
        for (int i=0; i < invoicesNumber; i++) {
            String invoiceMaxItemsString = Configuration.getInstance().getProperty("INVOICE_MAX_ITEMS");
            int invoiceMaxItems = Integer.parseInt(invoiceMaxItemsString);
            Invoice invoice = shopService.generateInvoice(invoiceMaxItems);
            invoices.add(invoice);      // save invoice to collection
            Handler fileHandler;
            String logFileName = null;
            try {
                String logDirectoryString = Configuration.getInstance().getProperty("LOG_DIRECTORY");
                logFileName = logDirectoryString + File.separator + "logger" + i + ".log";
                fileHandler = new FileHandler(logFileName);
            } catch (IOException e) {
                throw new CreateLogFileException("create log file " + logFileName + " error");
            }
            logger.addHandler(fileHandler);     // log to file
            logger.log(Level.INFO, () -> {
                StringBuilder builder = new StringBuilder();
                builder.append("\n\n");
                builder.append(invoice.getCustomer().toString());
                builder.append("\n\n");
                builder.append(invoice);
                return builder.toString();
            });
            logger.removeHandler(fileHandler);
            fileHandler.close();
        }
        logger.setUseParentHandlers(true);      // enable default console handler
        // analytics
        printAnalytics(invoices);
        // sorting
        sortInvoices(invoices);
    }
}
