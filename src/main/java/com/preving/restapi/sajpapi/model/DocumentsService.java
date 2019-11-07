package com.preving.restapi.sajpapi.model;

import com.preving.restapi.sajpapi.model.domains.documents.Document;
import com.preving.restapi.sajpapi.model.domains.documents.DocumentType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DocumentsService {


    List<Document> findDocumentsByCustomerId(int customerId, int documentType);

    Document findDocumentById(int documentId);

    List<DocumentType> findAllDocumentTypes();

    ResponseEntity<?> addPrivateDocument(HttpServletRequest request, Document document, int customerId, MultipartFile attachedFile);

    ResponseEntity<?> deleteDocument(HttpServletRequest request, int documentId);

    void downloadDocument(HttpServletResponse response, int documentId);

    void editPrivateDocument(HttpServletRequest request, int documentId, Document document, MultipartFile attachedFile);



}
