package next.school.cesar.desafionextextracaodeimagensexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXImageExtractor {
    public static void main(String[] args) {
        try {
            // Caminho para o arquivo XLSX de entrada
            String inputFilePath =
                    "Coloque aqui o caminho do arquivo";

            // Caminho para o diretório onde as imagens serão armazenadas
            String outputDirPath =
                    "Coloque aqui o caminho onde as imagens serão salvas";

            // Crie o diretório de saída, se ele não existir
            File outputDir = new File(outputDirPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Carregue o arquivo XLSX
            FileInputStream fis = new FileInputStream(new File(inputFilePath));
            Workbook workbook = new XSSFWorkbook(fis);

            // Obtenha todas as planilhas do arquivo
            List<? extends PictureData> pictures = workbook.getAllPictures();

            // Itere sobre as imagens
            for (PictureData picture : pictures) {
                // Gere um nome de arquivo único para cada imagem (você pode personalizar isso)
                String imageName = "imagem_" + System.currentTimeMillis() + "." + picture.suggestFileExtension();

                // Caminho completo para salvar a imagem
                String imagePath = outputDirPath + imageName;

                // Escreva os bytes da imagem em um arquivo
                byte[] imageBytes = picture.getData();
                FileOutputStream fos = new FileOutputStream(imagePath);
                fos.write(imageBytes);
                fos.close();

                System.out.println("Imagem salva em: " + imagePath);
            }

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}