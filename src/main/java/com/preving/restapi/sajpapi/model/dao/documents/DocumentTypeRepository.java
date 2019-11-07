package com.preving.restapi.sajpapi.model.dao.documents;

import com.preving.restapi.sajpapi.model.domains.documents.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {


    DocumentType findDocumentTypeById(int id);

    List<DocumentType> findAll();
}
