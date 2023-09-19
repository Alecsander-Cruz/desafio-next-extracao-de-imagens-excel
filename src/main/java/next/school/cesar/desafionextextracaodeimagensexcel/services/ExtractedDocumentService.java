package next.school.cesar.desafionextextracaodeimagensexcel.services;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import next.school.cesar.desafionextextracaodeimagensexcel.dao.ExtractedDocumentDao;
import next.school.cesar.desafionextextracaodeimagensexcel.entities.ExtractedDocument;
import next.school.cesar.desafionextextracaodeimagensexcel.repositories.ExtractedDocumentRepository;
import next.school.cesar.desafionextextracaodeimagensexcel.utils.ExtractorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExtractedDocumentService {

    @Autowired
    private ExtractedDocumentDao extractedDocumentDao;

    @Autowired
    private AmazonClientService amazonClientService;

    @Autowired
    private DocumentExtractorService documentExtractorService;

//    @Autowired
//    ExtractedDocumentService(AmazonClientService amazonClientService, DocumentExtractorService documentExtractorService){
//        this.amazonClientService = amazonClientService;
//        this.documentExtractorService = documentExtractorService;
//    }

    public void include(MultipartFile multipartFile){
        documentExtractorService.setFileAndOutput(multipartFile);
        documentExtractorService.saveImages();

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

//    public S3ObjectInputStream getImageByUrl(String url){
//        return amazonClientService.getFileFromS3Bucket(url);
//    }

}
