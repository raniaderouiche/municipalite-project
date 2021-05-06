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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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
	                /*
	                com.itextpdf.text.Image img=com.itextpdf.text.Image.getInstance("..\resources\\assets\\img\\bizertecover.png");
					img.setAlignment(com.itextpdf.text.Image.ALIGN_RIGHT);
					document.add(img);*/
	                
	                Paragraph p1 = new Paragraph();
	                Paragraph p2 = new Paragraph();
	                Paragraph p3 = new Paragraph();
	                Paragraph p4 = new Paragraph();

	                Font municipalityName=new Font();
	                municipalityName.setStyle(Font.BOLD);
	                municipalityName.setStyle(Font.UNDERLINE);
	                municipalityName.setSize(18);

	                Font documentType=new Font();
	                documentType.setSize(14);
	                documentType.setStyle(Font.UNDERLINE);
	                FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.BLACK);					
					
					Font documentFooter=new Font();
					
	                p1.add(m.getNom()+"\n");
	                p1.setAlignment(Element.ALIGN_CENTER);
	                p1.setFont(municipalityName);
	                FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.BLACK);
	                document.add(p1);

	                p2.add("Authorization\n \t\t\t\t\t==================================================");
	                p2.setAlignment(Element.ALIGN_CENTER);
	                p2.setFont(documentType);
	                FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.BLACK);
	                document.add(p2);

	                p3.add("\nAuthorization Number : "+auto.getId()+"\n");
	                p3.add("Citizen's name : "+auto.getNomCitoyen()+"\n");
	                p3.add("Citizen's CIN: "+auto.getCin()+"\n");
	                p3.add("\nSubject :\n \t I hereby grant this authorization to "+auto.getSujet()+"\n");
	                p3.add("\n\nBody :\n \t"+auto.getMsg());
	                document.add(p3);

	                p4.add("\n \nContact : \n");
	                p4.add("   number : "+m.getTel()+"\n");
	                p4.add("   email : "+m.getEmail()+"\n");
	                p4.add("   adress : "+m.getAdresse()+"\n");
	                p4.add("   web site : "+m.getWebsite()+"\n");
	                p4.add("\nDate : "+auto.getCreatedAt().getDayOfMonth()+'/'+auto.getCreatedAt().getMonthValue()+'/'+auto.getCreatedAt().getYear()+"\n");
	                p4.add("\nSignature \n");
	                p4.setAlignment(Element.ALIGN_BOTTOM);
	                FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.BLACK);
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

	    
	