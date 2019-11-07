package com.preving.restapi.sajpapi.model;

import com.preving.restapi.sajpapi.model.domains.Contact;
import com.preving.restapi.sajpapi.model.domains.customers.Customer;

import java.util.List;

public interface CustomerService {

    List findCustomersByFilter(String criterion);

    List<Customer> findAllByFilter(String criterion);

    Customer findCustomerById(int id);

    List<Contact> findContactByCustomerId(int id);

    Contact saveContactByCustomerId(Contact contact);
}
