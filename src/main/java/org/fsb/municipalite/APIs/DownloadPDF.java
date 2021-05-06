package org.fsb.municipalite.APIs;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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

                Paragraph p1 = new Paragraph();
                Paragraph p2 = new Paragraph();
                Paragraph p3 = new Paragraph();
                Paragraph p4 = new Paragraph();
                

                Font municipalityName=new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setSize(18);

                Font documentType=new Font();
                documentType.setSize(14);
                documentType.setStyle(Font.UNDERLINE);

                Font documentFooter=new Font();

                p1.add(m.getNom()+"\n");
                p1.setAlignment(Element.ALIGN_CENTER);
                p1.setFont(municipalityName);
                document.add(p1);

                p2.add("Complaint\n\n==================================================");
                p2.setAlignment(Element.ALIGN_CENTER);
                p2.setFont(documentType);
                document.add(p2);

                p3.add("Complaint Number : "+complaint.getId()+"\n");
                p3.add("Citizen's name : "+complaint.getNomCitoyen()+"\n");
                p3.add("Citizen's CIN: "+complaint.getCin()+"\n");
                p3.add("\n\n\nSubject : "+complaint.getSujet()+"\n");
                p3.add("\nBody :\n "+complaint.getMsg());
                document.add(p3);

                p4.add("\nContact : \n");
                p4.add("   number : "+m.getTel()+"\n");
                p4.add("   email : "+m.getEmail()+"\n");
                p4.add("   adress : "+m.getAdresse()+"\n");
                p4.add("   web site : "+m.getWebsite()+"\n");
                p4.add("\nDate : "+complaint.getCreatedAt().getDayOfMonth()+'/'+complaint.getCreatedAt().getMonthValue()+'/'+complaint.getCreatedAt().getYear()+"\n");
                p4.add("\nSignature \n");
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

