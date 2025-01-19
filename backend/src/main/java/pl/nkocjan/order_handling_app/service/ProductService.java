package pl.nkocjan.order_handling_app.service;

import org.openapitools.model.Product;
import org.openapitools.model.ProductCreateRequest;
import org.openapitools.model.ProductUpdateRequest;
import org.springframework.stereotype.Service;
import pl.nkocjan.order_handling_app.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements CRUDInterface<Product, ProductCreateRequest, ProductUpdateRequest> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(ProductCreateRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Product name already exists");
        }
        validProductName(request.getName());
        validProductDescription(request.getDescription());
        validProductPrice(Double.parseDouble(request.getBuyPrice().toString()), Double.parseDouble(request.getSellPrice().toString()));
        return productRepository.create(request);
    }

    public Product update(ProductUpdateRequest request) {
        productRepository.getById(UUID.fromString(request.getId()));
        validProductName(request.getName());
        validProductDescription(request.getDescription());
        validProductPrice(Double.parseDouble(request.getBuyPrice().toString()), Double.parseDouble(request.getSellPrice().toString()));
        return productRepository.update(request);
    }

    public int delete(UUID id) {
        int rowsAffected = productRepository.delete(id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Product not found");
        }
    }

    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public Product getById(UUID id) {
        return productRepository.getById(id);
    }


    private void validProductName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (name.length() < 3 || name.length() > 30) {
            throw new IllegalArgumentException("Product name must be between 3 and 30 characters");
        }
    }

    private void validProductDescription(String description) {
        if (description.length() < 5 || description.length() > 400) {
            throw new IllegalArgumentException("Product description must be between 5 and 400 characters");
        }
    }

    private void validProductPrice(Double buyPrice, Double sellPrice) {
        if (buyPrice == null || buyPrice < 0) {
            throw new IllegalArgumentException("Product buy-price must be non-negative");
        }
        if (sellPrice == null || sellPrice < 0) {
            throw new IllegalArgumentException("Product sell-price must be non-negative");
        }
        if (sellPrice < buyPrice) {
            throw new IllegalArgumentException("Product sell-price must be greater than buy-price");
        }
    }

}
