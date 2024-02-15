package io.zact.dto;

public class PurchaseDTO {
    public Long id;
    public Long productId;
    public Integer quantity;
    public double totalPrice;
    public PurchaseDTO() {
    }

    public PurchaseDTO(Long id, Long productId, Integer quantity, double totalPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "PurchaseDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}