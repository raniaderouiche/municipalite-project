package org.fsb.municipalite.services.APIs;

import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.fsb.municipalite.entities.*;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;

import java.io.File;
import java.io.FileOutputStream;


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


                Font municipalityName = new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setSize(18);


                Paragraph p0 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                p0.add("Tunisian republic\n");
                p0.setAlignment(Element.ALIGN_CENTER);


                Paragraph p1 = new Paragraph(m.getNom()+"\n",municipalityName);
                p1.setAlignment(Element.ALIGN_CENTER);


                Paragraph p2 = new Paragraph("Complaint\n\n\n");
                p2.setAlignment(Element.ALIGN_CENTER);


                Paragraph p3 = new Paragraph();
                p3.add("Complaint Number : "+complaint.getId()+"\n");
                p3.add("Citizen name : "+complaint.getNomCitoyen()+"\n");
                p3.add("Citizen CIN: "+complaint.getCin()+"\n");
                p3.add("\n\n\nSubject :"+complaint.getSujet()+"\n");
                p3.add("\nBody :\n \t"+complaint.getMsg()+"\n\n");

                Paragraph p4 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                p4.add("\nContact : \n");
                p4.add("    Number : "+m.getTel()+"\n");
                p4.add("    Email : "+m.getEmail()+"\n");
                p4.add("    Adress : "+m.getAdresse()+"\n");
                p4.add("    Web site : "+m.getWebsite()+"\n");
                p4.add("\nDate : "+complaint.getCreatedAt().getDayOfMonth()+'/'+complaint.getCreatedAt().getMonthValue()+'/'+complaint.getCreatedAt().getYear()+"\n");
                p4.add("\nSignature \n");
                p4.setAlignment(Element.ALIGN_BOTTOM);


                Image img = Image.getInstance("src/main/resources/assets/img/Coat_of_arms.png");
                //maintaining aspect ratio
                float imgW = 64;
                float imgH = 101;
                float padding = 15;
                img.setAbsolutePosition(padding,document.getPageSize().getHeight() - (imgH+padding));
                img.scaleAbsolute(imgW,imgH);


                document.add(img);
                document.add(p0);
                document.add(p1);
                document.add(p2);
                document.add(p3);
                document.add(new LineSeparator());
                document.add(p4);

                document.close();
                writer.close();

            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("itext PDF program executed");
    }

    public static void downloadPDF(Autorisation auto, Window window){
        try {
            MunicipaliteServiceImpl mc = new MunicipaliteServiceImpl();
            Municipalite m = mc.selectAll().get(0);
            FileChooser dirChooser = new FileChooser();
            File selectedDir = dirChooser.showSaveDialog(window);
            if(selectedDir != null){
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(selectedDir.getAbsolutePath()+".pdf"));
                document.open();


                Font municipalityName = new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setSize(18);

                Paragraph p0 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                p0.add("Tunisian republic\n");
                p0.setAlignment(Element.ALIGN_CENTER);


                Paragraph p1 = new Paragraph(m.getNom()+"\n",municipalityName);
                p1.setAlignment(Element.ALIGN_CENTER);


                Paragraph p2 = new Paragraph("Authorization\n\n\n");
                p2.setAlignment(Element.ALIGN_CENTER);



                Paragraph p3 = new Paragraph();
                p3.add("Authorization Number : "+auto.getId()+"\n");
                p3.add("Citizen name : "+auto.getNomCitoyen()+"\n");
                p3.add("Citizen CIN: "+auto.getCin()+"\n");
                p3.add("\n\n\nSubject :"+auto.getSujet()+"\n");
                p3.add("\n\nBody :\n \t"+auto.getMsg()+"\n\n");


                Paragraph p4 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                p4.add("\nContact : \n");
                p4.add("    Number : "+m.getTel()+"\n");
                p4.add("    Email : "+m.getEmail()+"\n");
                p4.add("    Adress : "+m.getAdresse()+"\n");
                p4.add("    Web site : "+m.getWebsite()+"\n");
                p4.add("\nDate : "+auto.getCreatedAt().getDayOfMonth()+'/'+auto.getCreatedAt().getMonthValue()+'/'+auto.getCreatedAt().getYear()+"\n");
                p4.add("\nSignature \n");
                p4.setAlignment(Element.ALIGN_BOTTOM);

                Image img = Image.getInstance("src/main/resources/assets/img/Coat_of_arms.png");
                //maintaining aspect ratio
                float imgW = 64;
                float imgH = 101;
                float padding = 15;
                img.setAbsolutePosition(padding,document.getPageSize().getHeight() - (imgH+padding));
                img.scaleAbsolute(imgW,imgH);


                document.add(img);
                document.add(p0);
                document.add(p1);
                document.add(p2);
                document.add(p3);
                document.add(new LineSeparator());
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

