package com.preving.restapi.sajpapi.model;

import com.preving.restapi.sajpapi.model.dao.customers.CustomerRepository;
import com.preving.restapi.sajpapi.model.dao.documents.DocumentTypeRepository;
import com.preving.restapi.sajpapi.model.dao.documents.DocumentsRepository;
import com.preving.restapi.sajpapi.model.domains.UsuarioMin;
import com.preving.restapi.sajpapi.model.domains.documents.Document;
import com.preving.restapi.sajpapi.model.domains.documents.DocumentType;
import com.preving.restapi.sajpapi.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;


@Service

public class DocumentsManager implements DocumentsService {

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private DocumentTypeRepository documentsTypeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static int GENERAL_DOCUMENTS_TYPE = 0;
    private static int PRIVATE_DOCUMENTS_TYPE = 1;

    private static final String CONTENT_TYPE_PDF = "application/pdf";
    private static final String CONTENT_TYPE_ZIP = "application/x-zip-compressed";
    private static final String CONTENT_TYPE_DOC = "application/msword";
    private static final String CONTENT_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static final String CONTENT_TYPE_ODT = "application/vnd.oasis.opendocument.text";
    private static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
    private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String CONTENT_TYPE_ODS = "application/vnd.oasis.opendocument.spreadsheet";
    private static final String CONTENT_TYPE_JPG = "image/jpg";
    private static final String CONTENT_TYPE_PNG = "image/png";




    public List<Document> findDocumentsByCustomerId(int customerId, int documentType) {

        List<Document> documents = new ArrayList<>();

        if (documentType == PRIVATE_DOCUMENTS_TYPE) {
            documents = documentsRepository.findAllByCustomerAndDeletedIsNull(customerRepository.findCustomerById(customerId));
        } else {
            documents = documentsRepository.findAllByCustomerIsNullAndDeletedIsNull();
        }

        return documents;

    }

    public List<Document> findDocumentsByCustomerIsNull() {
        return documentsRepository.findAllByCustomerIsNullAndDeletedIsNull();
    }

    public Document findDocumentById(int documentId) {
        return documentsRepository.findDocumentById(documentId);
    }

    public List<DocumentType> findAllDocumentTypes() {
        return documentsTypeRepository.findAll();
    }

    public ResponseEntity<?> addPrivateDocument(HttpServletRequest request, Document document, int customerId, MultipartFile attachedFile) {
        //Setting the document information using the muiltipart file
        UsuarioMin u = this.jwtTokenUtil.getUserWithRolesFromToken(request);
        Document auxDoc = null;
        document.setAttachedContentType(attachedFile.getContentType());
        document.setAttachedName(attachedFile.getOriginalFilename());
        document.getCustomer().setId(customerId);
        document.getCreatedBy().setId(Long.parseLong(u.getId().toString()));

        try {

            auxDoc = documentsRepository.save(document);
            String url = saveDocumentServer(customerId, auxDoc, attachedFile);
            documentsRepository.updateAttachedUrl(url, auxDoc.getId());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exc) {
            exc.printStackTrace();
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.BAD_REQUEST);
            //Get all errors
            body.put("errors", exc.getCause().getMessage().toString());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> deleteDocument(HttpServletRequest request, int documentId) {
        UsuarioMin u = this.jwtTokenUtil.getUserWithRolesFromToken(request);
        try {
            this.documentsRepository.documentLogicDelete(u.getId(), documentId);
        } catch (Exception ex) {
            ex.printStackTrace();
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.BAD_REQUEST);
            //Get all errors
            body.put("errors", ex.getCause().getMessage().toString());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        if (!deleteDocumentServer(documentId)) {
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public void downloadDocument(HttpServletResponse response, int documentId) {
        Document doc = null;
        File file = null;
        try {
            doc = this.findDocumentById(documentId);
            file = new File(doc.getAttachedUrl());
            if (file.exists()) {
                response.setContentType(doc.getAttachedContentType());
                String finalNombreDescarga = URLEncoder.encode(doc.getAttachedName(), "UTF-8");
                response.setHeader("Content-Disposition", "inline;filename=" + finalNombreDescarga);
                byte[] content = Files.readAllBytes(file.toPath());
                response.setContentLength(content.length);

                OutputStream out = response.getOutputStream();
                FileCopyUtils.copy(content, out);

                out.flush();
                out.close();
            }
        } catch (Exception ex) {

        }
    }

    private String saveDocumentServer(int customerId, Document document, MultipartFile attachedFile) throws IOException {
        String path = ("C:/home/preving/app/recursos/sajp/cliente/" + customerId + "/documento/" + document.getId());
        String url = ("C:/home/preving/app/recursos/sajp/cliente/" + customerId + "/documento/" + document.getId() + "/" + document.getId().toString()+this.contentType(attachedFile.getContentType()));
        File file = new File(url);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }

        } else {
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        attachedFile.transferTo(file);
        return url;
    }



    private boolean deleteDocumentServer(int documentId) {
        File file = new File(documentsRepository.findDocumentById(documentId).getAttachedUrl());
        Boolean borrado=null;
        if (file.exists()) {
            file.delete();

            return borrado=true;
        }else{
            borrado=false;
        }
    return borrado;

}

private String contentType(String contentType){
         String typeFile=null;
        switch (contentType){
            case CONTENT_TYPE_PDF:
                typeFile=".pdf";
                break;
            case  CONTENT_TYPE_ZIP:
                typeFile=".zip";
                break;
            case  CONTENT_TYPE_DOC:
                typeFile=".doc";
                break;
            case CONTENT_TYPE_DOCX:
                typeFile=".docx";
                break;
            case CONTENT_TYPE_ODT:
                typeFile=".odt";
                break;
            case CONTENT_TYPE_XLS:
                typeFile=".xls";
                break;
            case CONTENT_TYPE_XLSX:
                typeFile=".xlsx";
                break;
            case CONTENT_TYPE_ODS:
                typeFile=".ods";
                break;
            case CONTENT_TYPE_JPG:
                typeFile=".jpg";
                break;
            case CONTENT_TYPE_PNG:
                typeFile=".png";
                break;

        }
        return typeFile;

}


    public void editPrivateDocument(HttpServletRequest request,int documentId, Document document, MultipartFile attachedFile) {
        UsuarioMin u = this.jwtTokenUtil.getUserWithRolesFromToken(request);
            this.documentsRepository.documentEdit(u.getId(),documentId, document);
    }


}
