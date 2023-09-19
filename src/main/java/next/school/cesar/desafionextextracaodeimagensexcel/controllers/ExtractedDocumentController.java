package next.school.cesar.desafionextextracaodeimagensexcel.controllers;

import next.school.cesar.desafionextextracaodeimagensexcel.entities.ExtractedDocument;
import next.school.cesar.desafionextextracaodeimagensexcel.services.AmazonClientService;
import next.school.cesar.desafionextextracaodeimagensexcel.services.ExtractedDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/storage/")
public class ExtractedDocumentController {

    @Autowired
    ExtractedDocumentService extractedDocumentService;

    @Autowired
    AmazonClientService amazonClientService;

//    private AmazonClientService amazonClientService;
//    private DocumentExtractorService documentExtractorService;
//
//    @Autowired
//    BucketController(AmazonClientService amazonClientService, DocumentExtractorService documentExtractorService){
//        this.amazonClientService = amazonClientService;
//        this.documentExtractorService = documentExtractorService;
//    }


//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestPart(value = "file")MultipartFile file){
//        return this.amazonClientService.uploadFile(file);
//    }

//    @PostMapping("/uploadFile")
//    public List<String> uploadFile(@RequestPart(value = "file")MultipartFile multipartFile){
//        documentExtractorService.setFileAndOutput(multipartFile);
//        documentExtractorService.saveImages();
//
//        List<String> urls = new ArrayList<>();
//        List<File> imageList = ExtractorUtils.getAllImagesFromFolder();
//
//        if(!imageList.isEmpty()){
//            for(File image: imageList){
//                String url = this.amazonClientService.uploadImage(image);
//                urls.add(url);
//            }
//            return urls;
//
//        }
//        else{
//            urls.add("Nenhuma imagem foi extraída do documento!");
//            return urls;
//
//        }
//    }

    @PostMapping("/uploadFile")
    public void uploadFile(@RequestPart(value = "file") MultipartFile multipartFile){
        extractedDocumentService.include(multipartFile);
//        return new ResponseEntity<>()
    }

    @GetMapping("/documents")
    public ResponseEntity<List<ExtractedDocument>> getAllDocuments(){
        return new ResponseEntity<List<ExtractedDocument>>(extractedDocumentService.getAllDocuments(), HttpStatus.OK);
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<ExtractedDocument> getExtractedDocumentById(@PathVariable(value = "id") long id){
        ExtractedDocument extractedDocument = extractedDocumentService.getExtractedDocumentById(id);
        if(extractedDocument == null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(extractedDocument, HttpStatus.OK);
        }
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<ExtractedDocument> deleteDocumentById(@PathVariable(value = "id") long id){
        ExtractedDocument extractedDocumentToBeDeleted = extractedDocumentService.deleteExtractedDocument(id);
        if(extractedDocumentToBeDeleted == null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(extractedDocumentToBeDeleted, HttpStatus.OK);
        }
    }

//    @GetMapping("/image")
//    public S3ObjectInputStream getImageByUrl(@RequestPart(value = "url") String fileUrl){
////            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
////            return fileName;
//        return extractedDocumentService.getImageByUrl(fileUrl);
//    }


    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl){
        return this.amazonClientService.deleteFileFromS3Bucket(fileUrl);
    }
}
