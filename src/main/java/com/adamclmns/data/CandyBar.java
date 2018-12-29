package com.adamclmns.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

/**
 * The type Candy bar.
 */
@Document(collection = "SpringDataExample")
public class CandyBar {
    @Id
    private BigInteger id;

    private String name;
    private List<String> distribution;
    private String manufacturer;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<String> distribution) {
        this.distribution = distribution;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private BigInteger getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }
        CandyBar that = (CandyBar) obj;
        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append(" - ")
                .append("Distribution: ").append(distribution).append(" - ")
                .append("Manufacturer: ").append(manufacturer).append(" - ")
                .append("Description").append(description).append(" - ");
        return sb.toString();

    }

}
