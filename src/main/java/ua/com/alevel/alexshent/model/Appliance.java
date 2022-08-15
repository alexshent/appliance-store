package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;

/**
 * Electronic appliance basic class
 */
public abstract class Appliance {
    protected String id;
    protected String series;
    protected String screenType;
    protected BigDecimal price;

    public abstract String getType();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
