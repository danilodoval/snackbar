package com.example.snackbar.snackbar.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

    Order findById(String id);

    Page<Order> findAll(Pageable pageable);

}
