package com.preving.restapi.sajpapi.model.dao.contacts;

import com.preving.restapi.sajpapi.model.domains.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findContactByCustomerId(Integer id);

}
