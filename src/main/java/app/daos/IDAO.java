package app.daos;

import java.util.List;

public interface IDAO<T, I> {

    // 🔹 Read one by ID
    T read(I id);

    // 🔹 Read all
    List<T> readAll();

    // 🔹 Create new record
    T create(T t);

    // 🔹 Update record by ID
    T update(I id, T t);

    // 🔹 Delete record by ID
    void delete(I id);

    // 🔹 Validate if the primary key exists
    boolean validatePrimaryKey(I id);
}
