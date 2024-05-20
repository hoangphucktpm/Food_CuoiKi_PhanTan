package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "items")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
        @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i INNER JOIN i.ingredients ig WHERE ig.supplierName = :supplierName and i.onSpecial = true"),

})
public abstract class Item implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    protected String id;
    protected String name;
    protected double price;
    protected String description;
    @Column(name = "on_special")
    protected boolean onSpecial;
    @ManyToMany(mappedBy = "items")
    private Set<Ingredient> ingredients;

    public Item(String id, String name, double price, String description, boolean onSpecial) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.onSpecial = onSpecial;
    }

    public Item() {
    }

}