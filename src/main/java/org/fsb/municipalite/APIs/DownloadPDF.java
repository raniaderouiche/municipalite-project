package org.fsb.municipalite.APIs;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class DownloadPDF {


    public static void downloadPDF(Complaint complaint, Window window){
        try {
            MunicipaliteServiceImpl mc = new MunicipaliteServiceImpl();
            Municipalite m = mc.selectAll().get(0);
            FileChooser dirChooser = new FileChooser();
            File selectedDir = dirChooser.showSaveDialog(window);
            if(selectedDir != null){
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(selectedDir.getAbsolutePath()+".pdf"));
                document.open();
                
                Font documentType=new Font();
                documentType.setSize(14);

                Font municipalityName=new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setSize(18);
                
                Font documentFooter=new Font();
                documentFooter.setSize(10);
                
                Paragraph p1 = new Paragraph(m.getNom()+"\n",municipalityName);
                Paragraph p2 = new Paragraph("Complaint\n=========================================\n",documentType);
                Paragraph p3 = new Paragraph();
                Paragraph p4 = new Paragraph("\nContact : "+"   number : "+m.getTel()+"\n"+
                		"   email : "+m.getEmail()+"\n"+"   adress : "+m.getAdresse()+"\n"+
                		"   web site : "+m.getWebsite()+"\n"+
                		"\nDate : "+complaint.getCreatedAt().getDayOfMonth()+'/'+complaint.getCreatedAt().getMonthValue()+'/'+complaint.getCreatedAt().getYear()+"\n"+
                		"\nSignature \n",documentFooter);
                
                

                p1.setAlignment(Element.ALIGN_CENTER);
                document.add(p1);

                p2.setAlignment(Element.ALIGN_CENTER);
                document.add(p2);
                
                p3.add("Complaint Number : "+complaint.getId()+"\n");
                p3.add("Citizen's name : "+complaint.getNomCitoyen()+"\n");
                p3.add("Citizen's CIN: "+complaint.getCin()+"\n");
                p3.add("\n\n\nSubject : "+complaint.getSujet()+"\n");
                p3.add("\nBody :\n "+complaint.getMsg()+"\n\n");
                document.add(p3);
                
                document.add(new LineSeparator());
                p4.setAlignment(Element.ALIGN_BOTTOM);
                document.add(p4);

                document.close();
                writer.close();

            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("itext PDF program executed");
    }

}

