package com.preving.restapi.sajpapi.controller;

import com.preving.restapi.sajpapi.model.DocumentsService;
import com.preving.restapi.sajpapi.model.domains.Contact;
import com.preving.restapi.sajpapi.model.domains.customers.Customer;
import com.preving.restapi.sajpapi.model.CustomerService;
import com.preving.restapi.sajpapi.model.domains.documents.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
//@CrossOrigin ( origins = "*" , methods = { RequestMethod. GET , RequestMethod. POST } )
@RequestMapping(value = "/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DocumentsService documentsService;


    /**
     * Obtencion de un listado de CLIENTES con filtro por ID, Nombre/Razon social, CIF
     * @param active
     * @param queryStatus
     * @param criterion
     * @return
     */
    @RequestMapping(value = "customer-filter", method = RequestMethod.GET)
    public ResponseEntity<?> findAllByFilter(@RequestParam(value = "active") int active,
                                                        @RequestParam(value = "queryStatus") int queryStatus,
                                                        @RequestParam(value = "criterion") String criterion) {
        List<Customer> customers=null;

        try {
//            customers = this.customerService.findCustomersByFilter(criterion);
            customers = this.customerService.findAllByFilter(criterion);
        } catch(DataAccessException e) {

            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(customers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    /**
     * Obtencion de un CLIENTE con filtro por ID
     * @param customerId
     * @return
     */
    @RequestMapping(value = "{customerId}/data", method = RequestMethod.GET)
    public ResponseEntity<?> findCustomerById(@PathVariable("customerId") int customerId) {

        Customer customer=null;

        try {
            customer = this.customerService.findCustomerById(customerId);
        } catch(DataAccessException e) {

            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);

    }


    @RequestMapping(value = "{customerId}/documents/{documentType}", method = RequestMethod.GET)
    public ResponseEntity<?> findAllCustomerDocuments(@PathVariable(value = "customerId") int customerId,
                                                                 @PathVariable(value = "documentType") int documentType) {


        List<Document>docs=null;

        try {
            docs = this.documentsService.findDocumentsByCustomerId(customerId, documentType);
        } catch(DataAccessException e) {

            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(docs == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Document>>(docs, HttpStatus.OK);

    }


    /**
     * Obtencion de un CONTACTO por ID CLIENTE
     * @param customerId
     * @return
     */
    @RequestMapping(value = "{customerId}/contacts", method = RequestMethod.GET)
    public @ResponseBody List<Contact> findContactByCustomerId(@PathVariable("customerId") int customerId) {

        return this.customerService.findContactByCustomerId(customerId);
    }

//    /**
//     * Guardado de un CONTACTO por ID CLIENTE
//     * @param customerId
//     * @return
//     */
    @RequestMapping(value = "{customerId}/contacts/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveContactByCustomerId(@Valid @RequestBody Contact contact, BindingResult result) {

        if(result.hasErrors()) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        try {
            customerService.saveContactByCustomerId(contact);
        } catch(DataAccessException e) {

            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
