package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Invoice class
 */
public class Invoice {
    private List<Appliance> list;
    private Customer customer;
    private InvoiceType type;
    private LocalDateTime createdAt;

    public List<Appliance> getList() {
        return list;
    }

    public void setList(List<Appliance> list) {
        this.list = list;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal calculateTotal() {
        return list.stream()
                .map(Appliance::getPrice)
                .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
    }

    @Override
    public String toString() {
        return
                """
                        Invoice {
                        created at = %s,
                        type = %s,
                        list = %s
                        }""".formatted(createdAt.toString(), type.toString(), list.toString());
    }
}
