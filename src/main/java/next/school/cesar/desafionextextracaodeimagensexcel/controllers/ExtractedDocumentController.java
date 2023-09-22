package next.school.cesar.desafionextextracaodeimagensexcel.controllers;

import next.school.cesar.desafionextextracaodeimagensexcel.entities.ExtractedDocument;
import next.school.cesar.desafionextextracaodeimagensexcel.services.ExtractedDocumentService;
import next.school.cesar.desafionextextracaodeimagensexcel.utils.ExtractorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/storage/")
public class ExtractedDocumentController {

    @Autowired
    ExtractedDocumentService extractedDocumentService;

    @PostMapping("/uploadFile")
    public ResponseEntity<ExtractedDocument> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile){

        String extension = ExtractorUtils.getFileExtension(multipartFile);

        if(!extension.equals("xlsx")){
            return new ResponseEntity<>(new ExtractedDocument(0, Collections.singletonList("A extensão do arquivo não é permitida. Apenas arquivos XLSX são aceitos!")), HttpStatus.OK);
        }
        else{
            ExtractedDocument extractedDocument = extractedDocumentService.include(multipartFile);
            return new ResponseEntity<>(extractedDocument, HttpStatus.OK);
        }
    }

    @GetMapping("/documents")
    public ResponseEntity<List<ExtractedDocument>> getAllDocuments(){
        return new ResponseEntity<List<ExtractedDocument>>(extractedDocumentService.getAllDocuments(), HttpStatus.OK);
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<ExtractedDocument> getExtractedDocumentById(@PathVariable(value = "id") long id){
        ExtractedDocument extractedDocument = extractedDocumentService.getExtractedDocumentById(id);
        if(extractedDocument == null){
            return new ResponseEntity<>(
                    new ExtractedDocument(0, Collections.singletonList("Nenhum documento com  este ID foi encontrado!")),
                    HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(extractedDocument, HttpStatus.OK);
        }
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<String> deleteDocumentById(@PathVariable(value = "id") long id){
        ExtractedDocument extractedDocumentToBeDeleted = extractedDocumentService.deleteExtractedDocument(id);
        if(extractedDocumentToBeDeleted == null){
            return new ResponseEntity<>("Nada foi excluído, pois não havia nenhum documento com o ID \"" + id + "\".", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("O documento de ID \"" + id + "\" foi excluído junto com suas respectivas imagens.", HttpStatus.OK);
        }
    }
}
