package com.example.file.task.repository;


import com.example.file.task.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    List<Order> findByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "delete from orders where user_id=?1", nativeQuery = true)
    void deleteByUserId(Integer userId);
}
