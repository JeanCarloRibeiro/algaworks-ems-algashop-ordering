package com.algashop.ordering.infrastructure.persistence.repository;

import com.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {

}
