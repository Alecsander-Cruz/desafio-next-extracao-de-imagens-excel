package next.school.cesar.desafionextextracaodeimagensexcel;


import com.groupdocs.parser.Parser;
import com.groupdocs.parser.data.PageImageArea;
import com.groupdocs.parser.licensing.License;

import java.io.IOException;

public class ExtractImagesFromExcelInJava {
    public static void main(String[] args) throws IOException { // Main function to extract images from Excel in Java
        // Remove the watermark in output
        License lic = new License();
        lic.setLicense("GroupDocs.Parser.lic");

        // Create an instance of Parser class
        try (Parser parser = new Parser("sample.xlsx")) {
            // Extract images
            Iterable <PageImageArea> images = parser.getImages();
            // Check if images extraction is supported
            if (images == null) {
                System.out.println("Images extraction isn't supported");
                return;
            }

            // Iterate over images
            for (PageImageArea image: images) {
                // Print a page index, rectangle and image type:
                System.out.println(String.format("Page: %d, R: %s, Type: %s", image.getPage().getIndex(), image.getRectangle(), image.getFileType()));
            }
        }

    }

}