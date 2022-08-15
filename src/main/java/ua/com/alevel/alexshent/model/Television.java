package ua.com.alevel.alexshent.model;

/**
 * Television appliance class
 */
public class Television extends Appliance {
    private String diagonal;
    private String country;

    public String getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(String diagonal) {
        this.diagonal = diagonal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return
                """
                        Television {
                        id = %s,
                        series = %s,
                        screenType = %s,
                        price = %s,
                        diagonal = %s,
                        country = %s
                        }""".formatted(id, series, screenType, price.toString(), diagonal, country);
    }

    @Override
    public String getType() {
        return "Television";
    }
}
