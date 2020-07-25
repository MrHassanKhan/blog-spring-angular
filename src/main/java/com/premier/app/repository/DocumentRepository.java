package com.premier.app.repository;

import com.premier.app.domain.Document;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Document entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("select document from Document document where document.user.login = ?#{principal.username}")
    List<Document> findByUserIsCurrentUser();
}
