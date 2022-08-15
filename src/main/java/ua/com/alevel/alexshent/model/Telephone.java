package ua.com.alevel.alexshent.model;

/**
 * Telephone appliance class
 */
public class Telephone extends Appliance {
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return
                """
                        Telephone {
                        id = %s,
                        series = %s,
                        screenType = %s,
                        price = %s,
                        model = %s
                        }""".formatted(id, series, screenType, price.toString(), model);
    }

    @Override
    public String getType() {
        return "Telephone";
    }
}
