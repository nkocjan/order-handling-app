package pl.nkocjan.order_handling_app.repository;

import org.openapitools.model.Product;
import org.openapitools.model.ProductCreateRequest;
import org.openapitools.model.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.nkocjan.order_handling_app.service.CRUDInterface;

import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository implements CRUDInterface<Product, ProductCreateRequest, ProductUpdateRequest> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> getAll() {
        return jdbcTemplate.query("select * from product", BeanPropertyRowMapper.newInstance(Product.class));
    }

    public Product create(ProductCreateRequest request) {
        UUID id = UUID.randomUUID();
        jdbcTemplate.update("insert into product (id, name, description, buy_price, sell_price) VALUES (?,?,?,?,?)",
                id.toString(), request.getName(), request.getDescription(), request.getBuyPrice(), request.getSellPrice());
        Product tmp = new Product(id.toString(), request.getName(), request.getBuyPrice(), request.getSellPrice());
        if (request.getDescription() != null) {
            tmp.setDescription(request.getDescription());
        }
        return tmp;
    }

    public Product update(ProductUpdateRequest request) {
        jdbcTemplate.update("update product set name=?, description=?,buy_price=?,sell_price=? where id=?",
                request.getName(), request.getDescription(), request.getBuyPrice(), request.getSellPrice(), request.getId());
        Product tmp = new Product(request.getId(), request.getName(), request.getBuyPrice(), request.getSellPrice());
        if (request.getDescription() != null) {
            tmp.setDescription(request.getDescription());
        }
        return tmp;
    }

    public int delete(UUID id) {
        return jdbcTemplate.update("delete from product where id=?", id.toString());
    }

    public Product getById(UUID id) {
        return jdbcTemplate.queryForObject("select * from product where id=?", BeanPropertyRowMapper.newInstance(Product.class), id.toString());
    }

    public boolean existsByName(String name) {
        Integer count = jdbcTemplate.queryForObject("select count(*) from product where name=?", Integer.class, name);
        return count != null && count > 0;
    }
}
