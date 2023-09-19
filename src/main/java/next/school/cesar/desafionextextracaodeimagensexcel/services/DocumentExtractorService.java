package next.school.cesar.desafionextextracaodeimagensexcel.services;

import next.school.cesar.desafionextextracaodeimagensexcel.utils.ExtractorUtils;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;


@Service
public class DocumentExtractorService {

//    private String inputFilePath;
    private File fileToBeExtracted;
    private String outputDirPath;

    private int imageCounter = 0;

    public DocumentExtractorService() {
    }

    public void setFileAndOutput(MultipartFile multipartFile){

        try{
            // Caminho para o arquivo XLSX de entrada
            this.fileToBeExtracted = ExtractorUtils.convertMultiPartToFile(multipartFile);


            // Caminho para o diretório onde as imagens serão armazenadas
            this.outputDirPath = "E:\\backup\\documentos\\Documentos\\Alecsander\\Dev\\Next-Desafio-Grupo\\desafio-next-extracao-de-imagens-excel\\savedImages\\";

            tryToCreateDirectory();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void tryToCreateDirectory(){
        try {
            File outputDir = new File(outputDirPath);
            if(!outputDir.exists()){
                outputDir.mkdirs();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private Workbook loadXLSXFile(){
        try {
            FileInputStream fis = new FileInputStream(fileToBeExtracted);

            return new XSSFWorkbook(fis);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private List<? extends PictureData> getAllPicturesFromXLSX(){
        try{
            Workbook workbook = loadXLSXFile();

            return workbook.getAllPictures();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    // Colocar o retorno como String, null se tiver imagens anexadas no arquivo
    public void saveImages() {
        try {
            List<? extends PictureData> pictures = getAllPicturesFromXLSX();

            if (pictures.isEmpty()) {
                System.out.println("Nenhuma imagem foi encontrada!");
            } else {
                System.out.println(pictures.size() + " imagens foram encontradas!");

                // Itere sobre as imagens
                for (PictureData picture : pictures) {
                    // Gere um nome de arquivo único para cada imagem (você pode personalizar isso)
                    String imageName = "imagem_" + String.valueOf(new Date().getTime()) + String.valueOf(imageCounter++) + "." + picture.suggestFileExtension();

                    // Caminho completo para salvar a imagem
                    String imagePath = outputDirPath + imageName;

                    // Escreva os bytes da imagem em um arquivo
                    byte[] imageBytes = picture.getData();


                    FileOutputStream fos = new FileOutputStream(imagePath);
                    fos.write(imageBytes);
                    fos.close();

                    System.out.println("Imagem salva em: " + imagePath);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
