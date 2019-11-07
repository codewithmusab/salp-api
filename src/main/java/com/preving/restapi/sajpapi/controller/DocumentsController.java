package com.preving.restapi.sajpapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.preving.restapi.sajpapi.model.DocumentsService;
import com.preving.restapi.sajpapi.model.domains.documents.Document;
import com.preving.restapi.sajpapi.model.domains.documents.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping(value = "/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentsController {

    @Autowired
    private DocumentsService documentsService;

    @RequestMapping(value = "{documentId}", method = RequestMethod.GET)
    public ResponseEntity<?> findDocumentById(@PathVariable(value = "documentId") Integer documentId) {

        Document doc = null;

        try {
            doc = documentsService.findDocumentById(documentId);
        } catch(DataAccessException e) {

            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(doc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Document>(doc, HttpStatus.OK);


    }

    @RequestMapping(value = "types", method = RequestMethod.GET)
    public ResponseEntity<?> findAllDocumentTypes() {


        List<DocumentType> docTypes=null;
        try {
            docTypes = documentsService.findAllDocumentTypes();
        } catch(DataAccessException e) {

            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(docTypes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<DocumentType>>(docTypes, HttpStatus.OK);

    }


    @RequestMapping(value = "{customerId}/add-private", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPrivateDocument(HttpServletRequest request, @RequestParam("document") String documentJson,
            @RequestParam("attachedFile") MultipartFile attachedFile, @PathVariable("customerId") int customerId) {
        Gson gson = new GsonBuilder().create();
        Document document = gson.fromJson(documentJson, Document.class);
        return documentsService.addPrivateDocument(request,document, customerId, attachedFile);
    }

    @RequestMapping(value = "{documentId}/edit-private", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> editPrivateDocument(HttpServletRequest request,
                                                       @PathVariable(value = "documentId") int documentId,
                                                       @RequestParam("document") String documentJson,
                                                       @RequestParam("attachedFile") MultipartFile attachedFile) {


//        if(result.hasErrors()) {
//            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
//        }

        Gson gson = new GsonBuilder().create();
        @Valid
        Document document = gson.fromJson(documentJson, Document.class);

        try {
            documentsService.editPrivateDocument(request, documentId, document, attachedFile);
        } catch(DataAccessException e) {

            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(HttpStatus.OK);


    }

    @RequestMapping(value = "{documentId}/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteDocument (HttpServletRequest request, @PathVariable(value = "documentId") int documentId) {

           return documentsService.deleteDocument(request,documentId);

    }

    @RequestMapping(value = "{documentId}/download", method = RequestMethod.GET)
    public void downloadDocument(HttpServletResponse response, @PathVariable(value = "documentId") int documentId) {
        documentsService.downloadDocument(response,documentId);
    }

}
