package com.preving.restapi.sajpapi.model.dao.customers;

import com.preving.restapi.sajpapi.model.domains.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE UPPER(c.cifnif) LIKE UPPER(CONCAT('%',?1,'%'))" +
    "OR UPPER(c.name) LIKE UPPER(CONCAT('%',?1,'%'))" +
    "OR UPPER(c.surname) LIKE UPPER(CONCAT('%',?1,'%'))" +
    "OR UPPER(c.companyName) LIKE UPPER(CONCAT('%',?1,'%'))" +
    "OR UPPER(c.id) LIKE UPPER(CONCAT('%',?1,'%'))")
    List<Customer> findAllByFilter(String criterion);

    Customer findCustomerById(Integer id);

}
