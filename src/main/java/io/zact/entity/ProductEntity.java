package io.zact.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public double price;

    @Column(nullable = false)
    public Integer stock;
}
