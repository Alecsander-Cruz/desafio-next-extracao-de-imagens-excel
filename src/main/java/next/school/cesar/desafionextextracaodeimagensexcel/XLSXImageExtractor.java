package next.school.cesar.desafionextextracaodeimagensexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import next.school.cesar.desafionextextracaodeimagensexcel.services.ExtractedDocumentService;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XLSXImageExtractor {

    public static void main(String[] args) {

        ExtractedDocumentService extractedDocumentService = new ExtractedDocumentService();

        extractedDocumentService.setInputAndOutput();

        extractedDocumentService.saveImages();

//        try {
//
//            // Caminho para o arquivo XLSX de entrada
//            ExtractedDocumentService.setInputFilePath("E:\\backup\\documentos\\Documentos\\Alecsander\\Dev\\Next-Desafio-Grupo\\teste_planilha.xlsx");
//
//            // Caminho para o diretório onde as imagens serão armazenadas
//            ExtractedDocumentService.setOutputDirPath("E:\\backup\\documentos\\Documentos\\Alecsander\\Dev\\Next-Desafio-Grupo\\desafio-next-extracao-de-imagens-excel\\savedImages\\");
//
//
//            // Crie o diretório de saída, se ele não existir
////            File outputDir = new File(ExtractedDocumentMediator.getOutputDirPath());
////            if (!outputDir.exists()) {
////                outputDir.mkdirs();
////            }
//            ExtractedDocumentService.tryToCreateDirectory();
//
//            // Carregue o arquivo XLSX
//            FileInputStream fis = new FileInputStream(new File(ExtractedDocumentService.getInputFilePath()));
//            Workbook workbook = new XSSFWorkbook(fis);
//
//            // Obtenha todas as planilhas do arquivo
//            List<? extends PictureData> pictures = workbook.getAllPictures();
//
//            if(pictures.isEmpty()){
//                System.out.println("Nenhuma imagem foi encontrada!");
//            }
//            else{
//                System.out.println(pictures.size() + " imagens foram encontradas!");
//
//                // Itere sobre as imagens
//                for (PictureData picture : pictures) {
//                    // Gere um nome de arquivo único para cada imagem (você pode personalizar isso)
//                    String imageName = "imagem_" + System.currentTimeMillis() + "." + picture.suggestFileExtension();
//
//                    // Caminho completo para salvar a imagem
//                    String imagePath = ExtractedDocumentService.getOutputDirPath() + imageName;
//
//                    // Escreva os bytes da imagem em um arquivo
//                    byte[] imageBytes = picture.getData();
//                    FileOutputStream fos = new FileOutputStream(imagePath);
//                    fos.write(imageBytes);
//                    fos.close();
//
//                    System.out.println("Imagem salva em: " + imagePath);
//                }
//            }
//
//
//            fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}