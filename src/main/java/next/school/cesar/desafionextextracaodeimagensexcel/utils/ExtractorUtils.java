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

    public static String getFileExtension(MultipartFile multipartFile){
        System.out.println("Entrou no get file extension");

        String fileName = multipartFile.getOriginalFilename();
        System.out.println("pegou o nome do arquivo " + fileName);


        int index = fileName.lastIndexOf('.');
        System.out.println("pegou o index do . " + index);


        String extension = null;
        System.out.println("criou a String extension " + extension);


        if (index > 0) {
            System.out.println("entrou no if");
            extension = fileName.substring(index+1);
            System.out.println("adicionou a subtring de fileName a extension");
            System.out.println(extension);
        }

        return extension;
    }
}