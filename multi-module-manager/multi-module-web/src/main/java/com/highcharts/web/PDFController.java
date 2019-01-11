package com.highcharts.web;

import com.highcharts.common.base.Base;
import com.highcharts.common.base.BaseController;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-06-27 10:05
 **/
@RestController
public class PDFController extends BaseController {

    @RequestMapping(value = "/test/pdf/convent.do", produces = JSON_UTF8)
    public String getTxt(@RequestParam(value = "uploadFile") MultipartFile uploadFile) {
        File temp = null;
        String string = null;
        try {
            temp = File.createTempFile("temp", null);
            uploadFile.transferTo(temp);
            PDFParser pdfParser = new PDFParser(new RandomAccessFile(temp, "r"));
            pdfParser.parse();
            PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            string = pdfTextStripper.getText(pdDocument);
            pdDocument.close();
            return success(string);
        } catch (IOException e) {
            e.printStackTrace();
            return error("异常解析");
        }
    }
}
