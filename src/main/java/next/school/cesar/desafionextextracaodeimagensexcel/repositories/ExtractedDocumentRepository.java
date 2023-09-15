package next.school.cesar.desafionextextracaodeimagensexcel.repositories;

import next.school.cesar.desafionextextracaodeimagensexcel.entities.ExtractedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractedDocumentRepository extends JpaRepository<ExtractedDocument, Long> {
}
