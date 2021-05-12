package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Autorisation;

import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ElementListener;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
	import javafx.scene.control.RadioButton;
	import javafx.scene.control.TextField;
	import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;


public class AutorisationPrintController {

	    @FXML
	    private TextField citizenNameField;

	    @FXML
	    private TextField subjectField;
	    
	    @FXML
	    private ToggleGroup statusGroup;
	    
	    @FXML
	    private RadioButton processedRB;

	    @FXML
	    private RadioButton unprocessedRB;
	    
	    @FXML
	    private RadioButton selectedRadioButton;
	    @SuppressWarnings("unused")
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
	                Paragraph p3 = new Paragraph();
	                Paragraph p4 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
	                	                
	                
	                p1.add("Tunisian republic\n"+m.getNom());
	                p1.setAlignment(Element.ALIGN_CENTER);
	                p1.setFont(municipalityName);
	                             

	                p2.add("\nAuthorization\n ======================================================");
	                p2.setAlignment(Element.ALIGN_CENTER);
	                p2.setFont(documentType);

	                p3.add("\nAuthorization Number : "+auto.getId()+"\n");
	                p3.add("Citizen's name : "+auto.getNomCitoyen()+"\n");
	                p3.add("Citizen's CIN: "+auto.getCin()+"\n");
	                p3.add("\nSubject :\n \t I hereby grant this authorization to "+auto.getSujet()+"\n");
	                p3.add("\n\nBody :\n \t"+auto.getMsg()+"\n\n");
	                

	                p4.add("\nContact : \n");
	                p4.add("   number : "+m.getTel()+"\n");
	                p4.add("   email : "+m.getEmail()+"\n");
	                p4.add("   adress : "+m.getAdresse()+"\n");
	                p4.add("   web site : "+m.getWebsite()+"\n");
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

	}

	    
	