package com.highcharts.common.utils.pdf;

/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-06-26 17:49
 **/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
public class PDFTest {

    public static void main(String[] args) throws IOException {
        String string = null;
        try {
           // PDFParser pdfParser = new PDFParser(new RandomAccessFile(new File("F:\\pdf_text-master\\pdf\\Wiki环境下学生远程协作知识建构分析的模型反思与案例研究.pdf"), "r"));
           // pdfParser.parse();
            //PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
          //  PDFTextStripper pdfTextStripper = new PDFTextStripper();
         //   string = pdfTextStripper.getText(pdDocument);
            /**
             * 加密时候
             */
            PDDocument load = PDDocument.load(new File("F:\\pdf_text-master\\pdf\\Wiki环境下学生远程协作知识建构分析的模型反思与案例研究.pdf"), "123");
            load.setAllSecurityToBeRemoved(true);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.getText(load);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建文件对象
        File fileText = new File("F:\\pdf.txt");
        // 向文件写入对象写入信息
        try {
            FileWriter fileWriter = new FileWriter(fileText);
            // 写文件
            fileWriter.write(string);
            // 关闭
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(string);

    }
}
