package next.school.cesar.desafionextextracaodeimagensexcel.dao;

import next.school.cesar.desafionextextracaodeimagensexcel.entities.ExtractedDocument;
import next.school.cesar.desafionextextracaodeimagensexcel.repositories.ExtractedDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ExtractedDocumentDao")
public class ExtractedDocumentDao {

    @Autowired
    private ExtractedDocumentRepository extractedDocumentRepository;

    public void include(ExtractedDocument extractedDocument){
        extractedDocumentRepository.save(extractedDocument);
    }

    public List<ExtractedDocument> getAllDocuments(){
        return extractedDocumentRepository.findAll();
    }

    public ExtractedDocument getExtractedDocumentById(long id){
        Optional<ExtractedDocument> res = extractedDocumentRepository.findById(id);

        if(res.isPresent()){
            return res.get();
        }
        else{
            return null;
        }
    }


    public void deleteExtractedDocument(ExtractedDocument extractedDocument){
        extractedDocumentRepository.delete(extractedDocument);
    }
}
