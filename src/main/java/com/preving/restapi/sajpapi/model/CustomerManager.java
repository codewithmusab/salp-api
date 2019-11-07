package com.preving.restapi.sajpapi.model;

import com.preving.restapi.sajpapi.model.dao.contacts.ContactRepository;
import com.preving.restapi.sajpapi.model.dao.customers.CustomerRepository;
import com.preving.restapi.sajpapi.model.domains.Contact;
import com.preving.restapi.sajpapi.model.domains.customers.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.math.BigDecimal;

@Service
public class CustomerManager implements CustomerService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Customer> findCustomersByFilter(String criterion) {

        Query query = manager.createNamedQuery("findCustomersByFilter").setParameter("criterion", criterion);
        List<Customer> customers = query.getResultList();

        return customers;
    }

    @Override
    public List<Customer> findAllByFilter(String criterion) {
        return customerRepository.findAllByFilter(criterion);
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public List<Contact> findContactByCustomerId(int id) {
        return contactRepository.findContactByCustomerId(id);
    }

    @Override
    public Contact saveContactByCustomerId(Contact contact) {
        return contactRepository.save(contact);
    }

}

