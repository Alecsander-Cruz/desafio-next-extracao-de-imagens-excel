package next.school.cesar.desafionextextracaodeimagensexcel.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExtractorUtils {

    public static List<File> getAllImagesFromFolder(){
        try{
            final File folder = new File(
                    "E:\\backup\\documentos\\Documentos\\Alecsander\\Dev\\Next-Desafio-Grupo\\desafio-next-extracao-de-imagens-excel\\savedImages\\");
            final List<File> imageList = Arrays.asList(folder.listFiles());
            return imageList;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }
}
