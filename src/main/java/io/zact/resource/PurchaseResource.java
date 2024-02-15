package io.zact.resource;

import io.quarkus.logging.Log;
import io.zact.dto.PurchaseDTO;
import io.zact.entity.PurchaseEntity;
import io.zact.service.PurchaseService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/purchases")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseResource {

    @Inject
    PurchaseService purchaseService;

    @GET
    @Path("/{id}")
    public Response getPurchaseById(@PathParam("id") Long id) {
        try {
            Log.info("GET request received to fetch purchase with ID: " + id);
            PurchaseEntity purchase = purchaseService.findById(id);
            PurchaseDTO purchaseDTO = mapToDTO(purchase);
            Log.info("Purchase with ID " + id + " fetched successfully");
            return Response.ok(purchaseDTO).build();
        } catch (EntityNotFoundException e) {
            Log.info("Purchase with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            Log.error("Error while fetching purchase with ID: " + id);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while fetching purchase").build();
        }
    }

    @GET
    public Response getAllPurchases() {
        try {
            Log.info("GET request received to fetch all purchases");
            List<PurchaseEntity> purchases = purchaseService.findAll();
            List<PurchaseDTO> purchaseDTOs = mapToDTOs(purchases);
            Log.info("All purchases fetched successfully");
            return Response.ok(purchaseDTOs).build();
        } catch (Exception e) {
            Log.error("Error while fetching all purchases");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while fetching all purchases").build();
        }
    }

    private List<PurchaseDTO> mapToDTOs(List<PurchaseEntity> purchases) {
        List<PurchaseDTO> dtos = new ArrayList<>();
        for (PurchaseEntity purchase : purchases) {
            dtos.add(mapToDTO(purchase));
        }
        return dtos;
    }

    private PurchaseDTO mapToDTO(PurchaseEntity purchase) {
        PurchaseDTO dto = new PurchaseDTO();
        dto.id = purchase.id;
        dto.productId = purchase.product.id;
        dto.quantity = purchase.quantity;
        dto.totalPrice = purchase.totalPrice;
        return dto;
    }
}