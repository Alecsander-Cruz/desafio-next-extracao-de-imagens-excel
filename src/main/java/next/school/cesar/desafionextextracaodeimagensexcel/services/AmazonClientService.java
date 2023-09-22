package next.school.cesar.desafionextextracaodeimagensexcel.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonClientService {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.acessKey}")
    private String acessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializaAmazon(){
        AWSCredentials credentials = new BasicAWSCredentials(this.acessKey,this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    private String generateFileImageName(File file){
        return file.getName().replace(" ", "_");
    }

    private void uploadFileToS3Bucket(String fileName, File file){
        s3client.putObject(new PutObjectRequest(this.bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadImage(File file){
        String fileUrl = "";
        try{
            String fileName = generateFileImageName(file);
            fileUrl = this.endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileToS3Bucket(fileName, file);
            file.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
        return fileUrl;
    }

    public String deleteFileFromS3Bucket(String fileUrl){
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(this.bucketName, fileName);
        return "Successfully deleted";
    }

}
