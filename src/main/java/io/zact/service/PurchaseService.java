package io.zact.service;

import io.zact.entity.PurchaseEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@ApplicationScoped
public class PurchaseService {

    public PurchaseEntity findById(Long id) {
        PurchaseEntity entity = PurchaseEntity.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException("Purchase with id " + id + " not found.");
        }
        return entity;
    }

    public List<PurchaseEntity> findAll() {
        return PurchaseEntity.listAll();
    }
}