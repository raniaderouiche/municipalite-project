package org.fsb.municipalite.services.APIs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.fsb.municipalite.entities.*;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;
import org.fsb.municipalite.services.impl.RevenusServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


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

    //this is never used dhahrli
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
                Font documentType=new Font();
                FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.BLACK);

                Font municipalityName=new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setStyle(Font.UNDERLINE);
                municipalityName.setSize(18);

                Paragraph p0 = new Paragraph();
                Paragraph p1 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                Paragraph p2 = new Paragraph();
                Paragraph p3 = new Paragraph();
                Paragraph p4 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));


                p1.add("Tunisian republic\n"+m.getNom());
                p1.setAlignment(Element.ALIGN_CENTER);
                p1.setFont(municipalityName);

                p2.add("\nAuthorization\n");

                p2.setAlignment(Element.ALIGN_CENTER);
                p2.setFont(documentType);

                p3.add("\nAuthorization Number : "+auto.getId()+"\n");
                p3.add("Citizen's name : "+auto.getNomCitoyen()+"\n");
                p3.add("Citizen's CIN: "+auto.getCin()+"\n");
                p3.add("\nSubject :\n \t"+auto.getSujet()+"\n");
                p3.add("\n\nBody :\n \t"+auto.getMsg()+"\n\n");

                p4.add("\nContact : \n");
                p4.add("\tNumber : "+m.getTel()+"\n");
                p4.add("\tEmail : "+m.getEmail()+"\n");
                p4.add("\tAdress : "+m.getAdresse()+"\n");
                p4.add("\tWeb site : "+m.getWebsite()+"\n");
                p4.add("\nDate : "+auto.getCreatedAt().getDayOfMonth()+'/'+auto.getCreatedAt().getMonthValue()+'/'+auto.getCreatedAt().getYear()+"\n");
                p4.add("\nSignature \n");

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

    //activity reports
    public static void downloadPDF(Revenus rev, Depenses dep, Window window){
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
                FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL, BaseColor.BLACK);
                documentType.setSize(14);

                Font municipalityName=new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setStyle(Font.UNDERLINE);
                municipalityName.setSize(18);

                Font documentFooter=new Font();
                documentFooter.setSize(10);
                Paragraph p0 = new Paragraph();
                Paragraph p1 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                Paragraph p2 = new Paragraph();
                Paragraph p4 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));


                p1.add("Tunisian republic\n"+m.getNom());
                p1.setAlignment(Element.ALIGN_CENTER);
                p1.setFont(municipalityName);


                p2.add("\nRapport D'activité \n ======================================================");
                p2.setAlignment(Element.ALIGN_CENTER);
                p2.setFont(documentType);
                // Creating a table
                /*float [] ColumnWidth = {150F, 150F, 150F};*/
                //header
                PdfPTable table = new PdfPTable(2);
                PdfPCell cell = new PdfPCell(new Phrase("Depenses"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                PdfPCell cell1 = new PdfPCell(new Phrase("Revenus"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);







                p4.add("\nContact : \n");
                p4.add("   number : "+m.getTel()+"\n");
                p4.add("   email : "+m.getEmail()+"\n");
                p4.add("   adress : "+m.getAdresse()+"\n");
                p4.add("   web site : "+m.getWebsite()+"\n");
                p4.add("\nSignature \n");

                document.add(p1);
                document.add(p2);
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

