package next.school.cesar.desafionextextracaodeimagensexcel.services;

import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;


//@RestController
//@RequestMapping("/")
@Service
public class ExtractedDocumentService {

//    private AmazonClientService amazonClientService;
//
//    @Autowired
//    ExtractedDocumentService(AmazonClientService amazonClientService){
//        this.amazonClientService = amazonClientService;
//    }
//
//    @PostMapping
//    public void uploadImages(){
//        setInputAndOutput();
//        saveImages();
//    }

    private String inputFilePath;
    private String outputDirPath;

    public ExtractedDocumentService() {
    }

    public void setInputAndOutput(){

        try{
        // Caminho para o arquivo XLSX de entrada
        this.inputFilePath = "E:\\backup\\documentos\\Documentos\\Alecsander\\Dev\\Next-Desafio-Grupo\\teste_planilha.xlsx";

        // Caminho para o diretório onde as imagens serão armazenadas
        this.outputDirPath = "E:\\backup\\documentos\\Documentos\\Alecsander\\Dev\\Next-Desafio-Grupo\\desafio-next-extracao-de-imagens-excel\\savedImages\\";
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
            FileInputStream fis = new FileInputStream(new File(inputFilePath));

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
                    String imageName = "imagem_" + System.currentTimeMillis() + "." + picture.suggestFileExtension();

                    // Caminho completo para salvar a imagem
                    String imagePath = outputDirPath + imageName;

                    // Escreva os bytes da imagem em um arquivo
                    byte[] imageBytes = picture.getData();

//                    amazonClientService.uploadFileFile(imageBytes);


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


//        if(pictures.isEmpty()){
//            System.out.println("Nenhuma imagem foi encontrada!");
//        }
//        else{
//            System.out.println(pictures.size() + " imagens foram encontradas!");
//
//            // Itere sobre as imagens
//            for (PictureData picture : pictures) {
//                // Gere um nome de arquivo único para cada imagem (você pode personalizar isso)
//                String imageName = "imagem_" + System.currentTimeMillis() + "." + picture.suggestFileExtension();
//
//                // Caminho completo para salvar a imagem
//                String imagePath = ExtractedDocumentService.getOutputDirPath() + imageName;
//
//                // Escreva os bytes da imagem em um arquivo
//                byte[] imageBytes = picture.getData();
//                FileOutputStream fos = new FileOutputStream(imagePath);
//                fos.write(imageBytes);
//                fos.close();
//
//                System.out.println("Imagem salva em: " + imagePath);
//            }
//        }


}
