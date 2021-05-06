package org.fsb.municipalite.controllers;



import org.fsb.municipalite.entities.Autorisation;

import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;


import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
	import javafx.scene.control.RadioButton;
	import javafx.scene.control.TextField;
	import javafx.scene.control.ToggleGroup;
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

	                Paragraph p1 = new Paragraph();
	                Paragraph p2 = new Paragraph();
	                Paragraph p3 = new Paragraph();
	                Paragraph p4 = new Paragraph();
	                Paragraph p5 = new Paragraph();

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

	                p2.add("Autorisation");
	                p2.setAlignment(Element.ALIGN_CENTER);
	                p2.setFont(documentType);
	                document.add(p2);

	                p3.add("Autorisation Number : "+auto.getId()+"\n");
	                p3.add("Date : "+auto.getCreatedAt()+"\n");
	                p3.add("Citizen's name : "+auto.getNomCitoyen()+"\n");
	                p3.add("Citizen's CIN: "+auto.getCin()+"\n");
	                p3.add("Subject : "+auto.getSujet()+"\n");
	                p3.add("\nBody :\n "+auto.getMsg());
	                document.add(p3);

	                p4.add(auto.getMsg());
	                document.add(p4);

	                p5.add("\nContact : \n");
	                p5.add("   number : "+m.getTel()+"\n");
	                p5.add("   email : "+m.getEmail()+"\n");
	                p5.add("   adress : "+m.getAdresse()+"\n");
	                p5.add("   web site : "+m.getWebsite()+"\n");
	                p5.setAlignment(Element.ALIGN_BOTTOM);
	                document.add(p5);

	                document.close();
	                writer.close();

	            }

	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("itext PDF program executed");
	    }

	}

	    
	/*    
	     static void download(Autorisation autorisation, Window window) throws DocumentException {
	    	MunicipaliteServiceImpl mc = new MunicipaliteServiceImpl();
            Municipalite m = mc.selectAll().get(0);
            FileChooser dirChooser = new FileChooser();
            File selectedDir = dirChooser.showSaveDialog(window);
            
	    	try {
	    		if(selectedDir != null){
	            Document doc = new Document();
	    		PdfWriter writer =PdfWriter.getInstance(doc,new FileOutputStream(selectedDir.getAbsolutePath()+"Authorization.pdf"));
				 writer.open();
                doc.open();
                
				String format="dd/mm/yy hh:mm";
				SimpleDateFormat formater =new SimpleDateFormat(format);
				java.util.Date date = new java.util.Date();
				com.itextpdf.text.Image img=com.itextpdf.text.Image.getInstance("..\resources\\assets\\img\\bizertecover.png");
				img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
				doc.add(img);
				
                Font municipalityName=new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setSize(18);

                Font documentType=new Font();
                documentType.setSize(14);
                documentType.setStyle(Font.UNDERLINE);

                Font documentFooter=new Font();
				doc.add(new Paragraph("Municipality Name:"+m.getNom()+"\n"+
						"\n Address :"+m.getAdresse()+"\n"+
						"\nWebSite:"+m.getWebsite()+"\n"+
						"\nEmail:"+m.getEmail()+"\n"+
						"\nNumber:"+m.getTel()+"\n"+
						"==============================================="+
						"\n\nAuthorization:"+autorisation.getId()+"\n"+
						"\n\nTo:"+autorisation.getNomCitoyen()+"\n"+
						"Citizen's CIN: "+autorisation.getCin()+"\n"+
						"\nSubject:"+autorisation.getSujet()+"\n"+
						"\n\n\nCore:\nI hereby grant this authorization to "+autorisation.getMsg()+"\n"+
						"\n\n\n\n\n"+
						"\nDate of Authorization: "+formater.format(date)+""+
						"\nSincerely,\n"+
						"\nSignature"+
						FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.BLACK)));
	    	
	    		doc.close();
	    		
				writer.close();
	    	
            }*/
	    	
	    	      /*
					} catch (FileNotFoundException | DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	    	       	*/
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
	    
	    
	    	// System.out.println("itext PDF program executed");
	    
	    	 //Desktop.getDesktop().open(new File("Authorization.pdf"));
	    	 


	    
            
