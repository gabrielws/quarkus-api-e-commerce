package io.zact.resource;

import io.zact.entity.ProductEntity;
import io.zact.entity.PurchaseEntity;
import io.zact.service.ProductService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.quarkus.logging.Log;
import java.net.URI;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public Response getProducts() {
        try {
            Log.info("GET request received to fetch all products");
            List<ProductEntity> products = productService.findAll();
            return Response.ok(products).build();
        } catch (Exception e){
            Log.error("Error while fetching all products");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while fetching all products").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") Long id) {
        try {
            Log.info("GET request received to fetch product with ID: " + id);
            ProductEntity product = productService.findById(id);
            return Response.ok(product).build();
        } catch (EntityNotFoundException e){
            Log.info("Product with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e){
            Log.error("Error while fetching product with ID: " + id);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while fetching product").build();
        }
    }

    @POST
    public Response createProduct(ProductEntity product) {
        try {
            Log.info("POST request received to create a new product");
            productService.create(product);
            Log.info("Product created successfully");
            return Response.created(URI.create("/products/" + product.id)).build();
        } catch (IllegalArgumentException e) {
            Log.info("All fields (name, description, price and stock) are required");
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            Log.error("Internal server error occurred while creating a product: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while creating product").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Long id, ProductEntity product) {
        try {
            Log.info("PUT request received to update product with ID: " + id);
            productService.update(id, product);
            Log.info("Product updated successfully");
            return Response.ok().build();
        } catch (EntityNotFoundException e) {
            Log.info("Product with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            Log.error("Internal server error occurred while updating a product: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while updating product").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        try {
            Log.info("DELETE request received to delete product with ID: " + id);
            productService.delete(id);
            Log.info("Product deleted successfully");
            return Response.ok().build();
        } catch (EntityNotFoundException e) {
            Log.info("Product with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            Log.error("Internal server error occurred while deleting a product: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while deleting product").build();
        }
    }

    @POST
    @Path("/{productId}/purchases")
    public Response addPurchase(@PathParam("productId") Long productId, PurchaseEntity purchase) {
        try {
            Log.info("POST request received to add purchase for product with ID: " + productId);
            productService.addPurchase(productId, purchase);
            Log.info("Purchase added successfully");
            return Response.created(URI.create("/products/" + productId + "/purchases/" + purchase.id)).build();
        } catch (EntityNotFoundException e) {
            Log.info("Product or purchase not found while adding purchase");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            Log.info("Quantity must be greater than 0");
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } // Add the catch block below (before the last catch block)
        catch (Exception e) {
            Log.error("Internal server error occurred while adding purchase: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while adding purchase").build();
        }
    }

    @DELETE
    @Path("/{productId}/purchases/{purchaseId}")
    public Response removePurchase(@PathParam("productId") Long productId, @PathParam("purchaseId") Long purchaseId) {
        try {
            Log.info("DELETE request received to remove purchase with ID: " + purchaseId + " for product with ID: " + productId);
            productService.removePurchase(productId, purchaseId);
            Log.info("Purchase removed successfully");
            return Response.ok().build();
        } catch (EntityNotFoundException e) {
            Log.info("Product or purchase not found while removing purchase");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            Log.error("Internal server error occurred while removing purchase: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while removing purchase").build();
        }
    }
}