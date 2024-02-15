package io.zact.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public Long id;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PurchaseEntity> purchases = new ArrayList<>();

    @Column(nullable = false)
    public String name;

    @Column(nullable = true)
    public String description;

    @Column(nullable = false)
    public double price;

    @Column(nullable = false)
    public Integer stock;
}