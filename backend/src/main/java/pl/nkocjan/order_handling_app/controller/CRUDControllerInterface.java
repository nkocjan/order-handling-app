package pl.nkocjan.order_handling_app.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CRUDControllerInterface<T, C, U> {

    public ResponseEntity<List<T>> getAll();

    public ResponseEntity<T> getById(UUID id);

    public ResponseEntity<T> create(C request);

    public ResponseEntity<T> update(U request);

    public ResponseEntity<String> delete(UUID id);
}
