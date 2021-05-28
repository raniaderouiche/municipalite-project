package org.fsb.municipalite.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.fsb.municipalite.entities.Depenses;
import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.entities.Revenus;
import org.fsb.municipalite.services.impl.DepensesServiceImpl;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;
import org.fsb.municipalite.services.impl.RevenusServiceImpl;


import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class ActivityReportsPrintController {


    public  static void downloadPDF( String sec, LocalDate tf, Window window){
        Long revsum=0L;
        Long depsum=0L;
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
                PdfPTable table = new PdfPTable(3);
                PdfPCell cell = new PdfPCell(new Phrase("Date"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                PdfPCell cell1 = new PdfPCell(new Phrase("Depenses"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Phrase("Revenus"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);
                //* Adding cells to the table/*
                /*table.addCell(new Cell().add("Secteurs"));
                table.addCell(new Cell().add("Budgets"));
                table.addCell(new Cell().add(""));
                table.addCell(new Cell().add("1001"));
                table.addCell(new Cell().add("Designation"));
                /*=====================================*/
                /*table.addCell(new Cell().add("Name"));
                table.addCell(new Cell().add("Raju"));
                table.addCell(new Cell().add("Id"));
                table.addCell(new Cell().add("1001"));
                table.addCell(new Cell().add("Designation"));
                /*=====================================*/
                /*table.addCell(new Cell().add("Name"));
                table.addCell(new Cell().add("Raju"));
                table.addCell(new Cell().add("Id"));
                table.addCell(new Cell().add("1001"));
                table.addCell(new Cell().add("Designation"));
                /*=====================================*/

                if(sec.matches("Authorisation")) {

                        DepensesServiceImpl depenseService = new DepensesServiceImpl();
                        List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Authorisation'");
                        for (Depenses d : deplist) {
                            if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                                System.out.println(d);
                                PdfPCell cell3 = new PdfPCell(new Phrase(d.getDate_dep().toString()));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                                /*
                                PdfPCell cell4 = new PdfPCell(new Phrase(d.getSomme_dep()));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);*/
                                /*depsum=depsum+d.getSomme_dep();
                                System.out.println(depsum);*/
                            }
                        }

                    RevenusServiceImpl revenuService = new RevenusServiceImpl();
                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Authorisation'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            PdfPCell cell5 = new PdfPCell(new Phrase(r.getSomme_rev()));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell5);

                            /*revsum=revsum+r.getSomme_rev();
                            System.out.println(revsum);*/
                        }
                    }
                }
                else if(sec.matches("Complaints")) {
                    RevenusServiceImpl revenuService = new RevenusServiceImpl();
                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Complaints'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(r.getDate_rev() );
                            System.out.println(r.getSomme_rev());
                            revsum=revsum+r.getSomme_rev();
                            System.out.println(revsum);
                        }
                    }
                }
                else if(sec.matches("Events")) {
                    RevenusServiceImpl revenuService = new RevenusServiceImpl();
                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Events'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(r.getDate_rev() );
                            System.out.println(r.getSomme_rev());
                            revsum=revsum+r.getSomme_rev();
                            System.out.println(revsum);
                        }
                    }
                }
                else if(sec.matches("Materiel")) {
                    RevenusServiceImpl revenuService = new RevenusServiceImpl();
                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Materiel'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(r.getDate_rev() );
                            System.out.println(r.getSomme_rev());
                            revsum=revsum+r.getSomme_rev();
                            System.out.println(revsum);
                        }
                    }
                }
                else if(sec.matches("Teams")) {
                    RevenusServiceImpl revenuService = new RevenusServiceImpl();
                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Teams'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(r.getDate_rev() );
                            System.out.println(r.getSomme_rev());
                            revsum=revsum+r.getSomme_rev();
                            System.out.println(revsum);
                        }
                    }
                }
                else if(sec.matches("Projets")) {
                    RevenusServiceImpl revenuService = new RevenusServiceImpl();
                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Projets'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(r.getDate_rev() );
                            System.out.println(r.getSomme_rev());
                            revsum=revsum+r.getSomme_rev();
                            System.out.println(revsum);
                        }
                    }
                }
                else if(sec.matches("Tasks")) {
                    RevenusServiceImpl revenuService = new RevenusServiceImpl();
                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Tasks'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(r.getDate_rev() );
                            System.out.println(r.getSomme_rev());
                            revsum=revsum+r.getSomme_rev();
                            System.out.println(revsum);
                        }
                    }
                }
                //**************Depenses**************************\\

                else if(sec.matches("Complaints")) {
                    DepensesServiceImpl depenseService = new DepensesServiceImpl();
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Complaints'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(d.getDate_dep() );
                            System.out.println(d.getSomme_dep());
                            depsum=depsum+d.getSomme_dep();
                            System.out.println(depsum);
                        }
                    }
                }
                else if(sec.matches("Events")) {
                    DepensesServiceImpl depenseService = new DepensesServiceImpl();
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Events'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(d.getDate_dep() );
                            System.out.println(d.getSomme_dep());
                            depsum=depsum+d.getSomme_dep();
                            System.out.println(depsum);
                        }
                    }
                }
                else if(sec.matches("Materiel")) {
                    DepensesServiceImpl depenseService = new DepensesServiceImpl();
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Materiel'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(d.getDate_dep() );
                            System.out.println(d.getSomme_dep());
                            depsum=depsum+d.getSomme_dep();
                            System.out.println(depsum);
                        }
                    }
                }
                else if(sec.matches("Teams")) {
                    DepensesServiceImpl depenseService = new DepensesServiceImpl();
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Teams'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(d.getDate_dep() );
                            System.out.println(d.getSomme_dep());
                            depsum=depsum+d.getSomme_dep();
                            System.out.println(depsum);
                        }
                    }
                }
                else if(sec.matches("Projets")) {
                    DepensesServiceImpl depenseService = new DepensesServiceImpl();
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Projets'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(d.getDate_dep() );
                            System.out.println(d.getSomme_dep());
                            depsum=depsum+d.getSomme_dep();
                            System.out.println(depsum);
                        }
                    }
                }
                else if(sec.matches("Tasks")) {
                    DepensesServiceImpl depenseService = new DepensesServiceImpl();
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Tasks'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            System.out.println(d.getDate_dep() );
                            System.out.println(d.getSomme_dep());
                            depsum=depsum+d.getSomme_dep();
                            System.out.println(depsum);
                        }
                    }
                }





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
                document.add(table);
                document.close();
                writer.close();

            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("itext PDF program executed");
    }
}
