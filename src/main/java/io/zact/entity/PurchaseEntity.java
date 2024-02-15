package io.zact.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "purchases")
public class PurchaseEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public ProductEntity product;

    @Column(nullable = false)
    public Integer quantity;

    @Column(nullable = false)
    public double totalPrice;

    @CreationTimestamp
    public ZonedDateTime purchaseDate;
}
