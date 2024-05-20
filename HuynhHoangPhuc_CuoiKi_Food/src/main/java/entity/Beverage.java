package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "beverages")
public class Beverage extends Item {
    @Enumerated(EnumType.STRING)
    private Size size;
    @Column(name = "supplier_name")
    private String supplierName;

    public Beverage(String id, String name, double price, String description, boolean onSpecial, Size size, String supplierName) {
        super(id, name, price, description, onSpecial);
        this.size = size;
        this.supplierName = supplierName;
    }

    public Beverage() {
    }


}