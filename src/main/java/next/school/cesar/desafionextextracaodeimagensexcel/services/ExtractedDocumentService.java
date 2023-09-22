package next.school.cesar.desafionextextracaodeimagensexcel.services;

import next.school.cesar.desafionextextracaodeimagensexcel.dao.ExtractedDocumentDao;
import next.school.cesar.desafionextextracaodeimagensexcel.entities.ExtractedDocument;
import next.school.cesar.desafionextextracaodeimagensexcel.utils.ExtractorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ExtractedDocumentService {

    @Autowired
    private ExtractedDocumentDao extractedDocumentDao;

    @Autowired
    private AmazonClientService amazonClientService;

    @Autowired
    private DocumentExtractorService documentExtractorService;


    public ExtractedDocument include(MultipartFile multipartFile){
        documentExtractorService.setFileAndOutput(multipartFile);

        if(documentExtractorService.saveImages() == null){
            return new ExtractedDocument(0, Collections.singletonList("Nenhuma imagem foi extraída do documento!"));
        }
        else {
            List<String> urls = new ArrayList<>();
            List<File> imageList = ExtractorUtils.getAllImagesFromFolder();

            if(!imageList.isEmpty()){
                for(File image: imageList){
                    String url = this.amazonClientService.uploadImage(image);
                    urls.add(url);
                }
            }
            else{
                urls.add("Nenhuma imagem foi extraída do documento!");
            }

            ExtractedDocument extractedDocument = new ExtractedDocument();
            extractedDocument.setImagesListLink(urls);

            extractedDocumentDao.include(extractedDocument);
            return extractedDocument;
        }

    }

    public List<ExtractedDocument> getAllDocuments(){
        return extractedDocumentDao.getAllDocuments();
    }

    public ExtractedDocument getExtractedDocumentById(long id){
        return extractedDocumentDao.getExtractedDocumentById(id);
    }

    public ExtractedDocument deleteExtractedDocument(long id){
        ExtractedDocument documentToBeDeleted = extractedDocumentDao.getExtractedDocumentById(id);

        if(documentToBeDeleted != null){
            for (String imageToBeDeleted : documentToBeDeleted.getImagesListLink()){
                System.out.println(imageToBeDeleted);
                amazonClientService.deleteFileFromS3Bucket(imageToBeDeleted);
            }
            extractedDocumentDao.deleteExtractedDocument(documentToBeDeleted);
            return documentToBeDeleted;
        }
        else{
            return null;
        }
    }
}
