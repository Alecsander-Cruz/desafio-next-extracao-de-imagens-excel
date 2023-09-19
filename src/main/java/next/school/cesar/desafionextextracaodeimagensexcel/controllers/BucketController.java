package next.school.cesar.desafionextextracaodeimagensexcel.controllers;

import next.school.cesar.desafionextextracaodeimagensexcel.services.AmazonClientService;
import next.school.cesar.desafionextextracaodeimagensexcel.services.ExtractedDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    private AmazonClientService amazonClientService;

    @Autowired
    BucketController(AmazonClientService amazonClientService){
        this.amazonClientService = amazonClientService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file")MultipartFile file){
        return this.amazonClientService.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl){
        return this.amazonClientService.deleteFileFromS3Bucket(fileUrl);
    }
}
