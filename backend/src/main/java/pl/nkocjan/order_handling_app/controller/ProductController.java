package pl.nkocjan.order_handling_app.controller;

import org.openapitools.model.Product;
import org.openapitools.model.ProductCreateRequest;
import org.openapitools.model.ProductUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nkocjan.order_handling_app.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController implements CRUDControllerInterface<Product, ProductCreateRequest, ProductUpdateRequest> {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.getAll());
    }

    @Override
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.getById(id));
    }

    @Override
    public ResponseEntity<Product> create(@RequestBody ProductCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.create(request));
    }

    @Override
    public ResponseEntity<Product> update(@RequestBody ProductUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.update(request));
    }

    @Override
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return ResponseEntity.ok("Product deleted");
    }
}
