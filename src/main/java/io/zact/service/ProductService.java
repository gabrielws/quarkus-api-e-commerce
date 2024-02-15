package io.zact.service;

import io.quarkus.logging.Log;
import io.zact.entity.ProductEntity;
import io.zact.entity.PurchaseEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProductService {

    public ProductEntity findById(Long id) {
        ProductEntity entity = ProductEntity.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException("Product with id " + id + " not found.");
        }
        return entity;
    }

    public List<ProductEntity> findAll() {
        return ProductEntity.listAll();
    }

    @Transactional
    public void create(ProductEntity product) {
        if (product.name == null || product.description == null || product.price <=0 || product.stock == null) {
            throw new IllegalArgumentException("All fields (name, description, price and stock) are required.");
        }
        try {
            ProductEntity entity = new ProductEntity();
            entity.name = product.name;
            entity.description = product.description;
            entity.price = product.price;
            entity.stock = product.stock;
            entity.persist();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating product");
        }
    }

    @Transactional
    public void update(Long id, ProductEntity product) {
        if (product.name == null || product.description == null || product.price <=0 || product.stock == null) {
            throw new IllegalArgumentException("All fields (name, description, price and stock) are required.");
        }
        try {
            ProductEntity entity = ProductEntity.findById(id);
            if (entity == null) {
                throw new EntityNotFoundException();
            }
            entity.name = product.name;
            entity.description = product.description;
            entity.price = product.price;
            entity.stock = product.stock;
            entity.persist();
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Product with id " + id + " not found.");
        }
        catch (Exception e) {
            throw new RuntimeException("Error while updating product");
        }
    }

    @Transactional
    public void delete(Long id) {
        ProductEntity entity = ProductEntity.findById(id);
        if (entity == null) {
            throw new RuntimeException("Product with id " + id + " not found.");
        }
        ProductEntity.deleteById(id);
    }

    @Transactional
    public void addPurchase(Long productId, PurchaseEntity purchase) {
        if(purchase.quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        ProductEntity product = ProductEntity.findById(productId);
        if (product == null) {
            throw new EntityNotFoundException("Product with id " + productId + " not found.");
        }
        purchase.product = product;
        purchase.totalPrice = purchase.quantity * product.price;
        product.purchases.add(purchase);
        product.persist();
    }

    @Transactional
    public void removePurchase(Long productId, Long purchaseId) {
        ProductEntity product = ProductEntity.findById(productId);
        if (product == null) {
            throw new EntityNotFoundException("Product with id " + productId + " not found.");
        }
        PurchaseEntity purchase = PurchaseEntity.findById(purchaseId);
        if (purchase == null) {
            throw new EntityNotFoundException("Purchase with id " + purchaseId + " not found.");
        }
        product.purchases.remove(purchase);
        product.persist();
        purchase.delete();
    }
}