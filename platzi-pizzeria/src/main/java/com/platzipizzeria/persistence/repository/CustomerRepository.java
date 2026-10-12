package com.platzipizzeria.persistence.repository;

import com.platzipizzeria.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends ListCrudRepository<CustomerEntity, String> {

    @Query(value = "SELECT C FROM CustomerEntity C WHERE C.phoneNumber = :phone")
    CustomerEntity findByPhone(@Param ("phone") String phone);
}
