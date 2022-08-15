package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Factory for appliances
 */
public class ApplianceFactory {

    private ApplianceFactory() {}

    public static Appliance create(Map<String, String> applianceColumns) {
        String type = applianceColumns.get("type");
        String series = applianceColumns.get("series");
        String screenType = applianceColumns.get("screenType");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(applianceColumns.get("price")));
        Appliance appliance = null;
        if (type.equals("Telephone")) {
            String model = applianceColumns.get("model");
            Telephone telephone = new Telephone();
            telephone.setModel(model);
            appliance = telephone;
        } else if (type.equals("Television")) {
            String diagonal = applianceColumns.get("diagonal");
            String country = applianceColumns.get("country");
            Television television = new Television();
            television.setDiagonal(diagonal);
            television.setCountry(country);
            appliance = television;
        }
        if (appliance != null) {
            appliance.setId(UUID.randomUUID().toString());
            appliance.setSeries(series);
            appliance.setScreenType(screenType);
            appliance.setPrice(price);
        }
        return appliance;
    }
}
