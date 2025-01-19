package pl.nkocjan.order_handling_app.service;

import java.util.List;
import java.util.UUID;

public interface CRUDInterface<T, C, U> {
    public T create(C request);

    public T update(U request);

    public List<T> getAll();

    public T getById(UUID id);

    public int delete(UUID id);
}
