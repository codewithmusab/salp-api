package com.preving.restapi.sajpapi.model.dao.documents;

import com.preving.restapi.sajpapi.model.domains.customers.Customer;
import com.preving.restapi.sajpapi.model.domains.documents.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Integer> {

    Document findDocumentById(int documentId);

    List<Document>findAllByCustomerAndDeletedIsNull(Customer customer);

    List<Document>findAllByCustomerIsNullAndDeletedIsNull();

    @Modifying
    @Transactional
    @Query("update Document d set borrado=CURRENT_TIMESTAMP, borrado_por=:deleted_by where id=:document_id")
    void documentLogicDelete(@Param("deleted_by") int deleted_by, @Param("document_id") int document_id);

    @Modifying
    @Transactional
    @Query("update Document d set d.documentType=:#{#doc.documentType}," +
            "nombre=:#{#doc.name}, d.attachedUrl=:#{#doc.attachedUrl}, d.attachedContentType=:#{#doc.attachedContentType}, d.attachedName=:#{#doc.attachedName}, d.date=:#{#doc.date}, d.visibleExtranet=:#{#doc.visibleExtranet}, d.visibleUntil=:#{#doc.visibleUntil}, d.description=:#{#doc.description}, d.internalObservations=:#{#doc.internalObservations}, modificado= CURRENT_TIMESTAMP, modificado_por=:edit_by where d.id=:document_id and d.deleted is null")
    void documentEdit(@Param("edit_by") int edit_by,@Param("document_id") int document_id,@Param("doc") Document doc);


    @Modifying
    @Transactional
    @Query("update Document d set url_adjunto=:url where id=:document_id")
    void updateAttachedUrl(@Param("url") String url, @Param("document_id") int document_id);

}
