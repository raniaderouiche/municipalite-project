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
import java.util.ArrayList;
import java.util.Iterator;
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
                Paragraph p1 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                Paragraph p2 = new Paragraph();
                Paragraph p3 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                Paragraph p4 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                Paragraph p5 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));


                p1.add("Tunisian republic\n"+m.getNom());
                p1.setAlignment(Element.ALIGN_CENTER);
                p1.setFont(municipalityName);


                p2.add("\nRapport D'activité \n ======================================================");
                p2.setAlignment(Element.ALIGN_CENTER);
                p2.setFont(documentType);

                p5.add("\n Generée le :"+tf.toString());
                 /*-------tableheader------------*/
                PdfPTable table = new PdfPTable(2);
                PdfPCell cell = new PdfPCell(new Phrase("Revenus"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                PdfPCell cell1 = new PdfPCell(new Phrase("Depenses"));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                /*--------services--------*/
                DepensesServiceImpl depenseService = new DepensesServiceImpl();
                RevenusServiceImpl revenuService = new RevenusServiceImpl();
                /*---------------*/
                List<String> listr = new ArrayList<String>();
                List<String> listdep = new ArrayList<String>();
                /*------authorisation----------*/
                if(sec.matches("Authorisation")) {



                        List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Authorisation'");
                        for (Depenses d : deplist) {
                            if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                                listdep.add(Long.toString(d.getSomme_dep()));
                                depsum=depsum+d.getSomme_dep();

                            }
                        }


                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Authorisation'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            listr.add(Long.toString(r.getSomme_rev()));
                            revsum=revsum+r.getSomme_rev();

                        }
                    }
                    int sd = listdep.size();
                    int sr= listr.size();
                    boolean test1=sd<=sr;
                    Iterator iterator = listdep.iterator();
                    Iterator iterator1 = listr.iterator();
                    if (test1) {
                        while (iterator1.hasNext()) {
                            PdfPCell cell2 = new PdfPCell(new Phrase(iterator1.next().toString()));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell2);
                            if (iterator.hasNext()) {
                                PdfPCell cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }
                            else {
                                PdfPCell cell3 = new PdfPCell(new Phrase("-"));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }

                        }
                    }
                    else {
                        while (iterator.hasNext()) {
                            PdfPCell cell3;
                            cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell3);
                            if(iterator1.hasNext()) {
                                PdfPCell cell4 = new PdfPCell(new Phrase(iterator1.next().toString()));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);
                            }
                            else{
                                PdfPCell cell5 = new PdfPCell(new Phrase("-"));
                                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell5);
                            }
                        }
                        }
                }

                /*------Complaint----------*/
                else if(sec.matches("Complaints")) {

                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Complaints'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            listr.add(Long.toString(r.getSomme_rev()));
                            revsum=revsum+r.getSomme_rev();

                        }
                    }


                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Complaints'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            listdep.add(Long.toString(d.getSomme_dep()));
                            depsum=depsum+d.getSomme_dep();
                        }
                    }
                    int sd = listdep.size();
                    int sr= listr.size();
                    boolean test1=sd<=sr;
                    Iterator iterator = listdep.iterator();
                    Iterator iterator1 = listr.iterator();
                    if (test1) {
                        while (iterator1.hasNext()) {
                            PdfPCell cell2 = new PdfPCell(new Phrase(iterator1.next().toString()));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell2);
                            if (iterator.hasNext()) {
                                PdfPCell cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }
                            else {
                                PdfPCell cell3 = new PdfPCell(new Phrase("-"));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }

                        }
                    }
                    else {
                        while (iterator.hasNext()) {
                            PdfPCell cell3;
                            cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell3);
                            if (iterator1.hasNext()) {
                                PdfPCell cell4 = new PdfPCell(new Phrase(iterator1.next().toString()));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);
                            } else {
                                PdfPCell cell5 = new PdfPCell(new Phrase("-"));
                                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell5);
                            }
                        }
                    }
                }
                /*------Events----------*/
                else if(sec.matches("Events")) {

                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Events'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            listr.add(Long.toString(r.getSomme_rev()));
                            revsum = revsum + r.getSomme_rev();
                        }
                    }
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Events'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            listdep.add(Long.toString(d.getSomme_dep()));
                            depsum = depsum + d.getSomme_dep();
                        }
                    }

                    int sd = listdep.size();
                    int sr = listr.size();
                    boolean test1 = sd <= sr;
                    Iterator iterator = listdep.iterator();
                    Iterator iterator1 = listr.iterator();
                    if (test1) {
                        while (iterator1.hasNext()) {
                            PdfPCell cell2 = new PdfPCell(new Phrase(iterator1.next().toString()));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell2);
                            if (iterator.hasNext()) {
                                PdfPCell cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            } else {
                                PdfPCell cell3 = new PdfPCell(new Phrase("-"));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }

                        }
                    } else {
                        while (iterator.hasNext()) {
                            PdfPCell cell3;
                            cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell3);
                            if (iterator1.hasNext()) {
                                PdfPCell cell4 = new PdfPCell(new Phrase(iterator1.next().toString()));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);
                            } else {
                                PdfPCell cell5 = new PdfPCell(new Phrase("-"));
                                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell5);
                            }
                        }
                    }
                }
                /*------Materiel----------*/
                else if(sec.matches("Materiel")) {

                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Materiel'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            listr.add(Long.toString(r.getSomme_rev()));
                            revsum = revsum + r.getSomme_rev();
                        }
                    }

                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Events'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            listdep.add(Long.toString(d.getSomme_dep()));
                            depsum = depsum + d.getSomme_dep();

                        }
                    }
                    int sd = listdep.size();
                    int sr = listr.size();
                    boolean test1 = sd <= sr;
                    Iterator iterator = listdep.iterator();
                    Iterator iterator1 = listr.iterator();
                    if (test1) {
                        while (iterator1.hasNext()) {
                            PdfPCell cell2 = new PdfPCell(new Phrase(iterator1.next().toString()));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell2);
                            if (iterator.hasNext()) {
                                PdfPCell cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            } else {
                                PdfPCell cell3 = new PdfPCell(new Phrase("-"));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }

                        }
                    } else {
                        while (iterator.hasNext()) {
                            PdfPCell cell3;
                            cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell3);
                            if (iterator1.hasNext()) {
                                PdfPCell cell4 = new PdfPCell(new Phrase(iterator1.next().toString()));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);
                            } else {
                                PdfPCell cell5 = new PdfPCell(new Phrase("-"));
                                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell5);
                            }
                        }

                    }
                }
                /*------teams----------*/
                else if(sec.matches("Teams")) {

                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Teams'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            listr.add(Long.toString(r.getSomme_rev()));
                            revsum=revsum+r.getSomme_rev();
                        }
                    }

                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Teams'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            listdep.add(Long.toString(d.getSomme_dep()));
                            depsum=depsum+d.getSomme_dep();
                        }
                    }

                    int sd = listdep.size();
                    int sr= listr.size();
                    boolean test1=sd<=sr;
                    Iterator iterator = listdep.iterator();
                    Iterator iterator1 = listr.iterator();
                    if (test1) {
                        while (iterator1.hasNext()) {
                            PdfPCell cell2 = new PdfPCell(new Phrase(iterator1.next().toString()));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell2);
                            if (iterator.hasNext()) {
                                PdfPCell cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }
                            else {
                                PdfPCell cell3 = new PdfPCell(new Phrase("-"));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }

                        }
                    }
                    else {
                        while (iterator.hasNext()) {
                            PdfPCell cell3;
                            cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell3);
                            if(iterator1.hasNext()) {
                                PdfPCell cell4 = new PdfPCell(new Phrase(iterator1.next().toString()));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);
                            }
                            else{
                                PdfPCell cell5 = new PdfPCell(new Phrase("-"));
                                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell5);
                            }
                        }
                    }

                }
                /*------Projet----------*/
                else if(sec.matches("Projets")) {

                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Projets'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            listr.add(Long.toString(r.getSomme_rev()));
                            revsum=revsum+r.getSomme_rev();
                        }
                    }
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Projets'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            listdep.add(Long.toString(d.getSomme_dep()));
                            depsum=depsum+d.getSomme_dep();
                        }
                    }

                    int sd = listdep.size();
                    int sr= listr.size();
                    boolean test1=sd<=sr;
                    Iterator iterator = listdep.iterator();
                    Iterator iterator1 = listr.iterator();
                    if (test1) {
                        while (iterator1.hasNext()) {
                            PdfPCell cell2 = new PdfPCell(new Phrase(iterator1.next().toString()));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell2);
                            if (iterator.hasNext()) {
                                PdfPCell cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }
                            else {
                                PdfPCell cell3 = new PdfPCell(new Phrase("-"));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }

                        }
                    }
                    else {
                        while (iterator.hasNext()) {
                            PdfPCell cell3;
                            cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell3);
                            if(iterator1.hasNext()) {
                                PdfPCell cell4 = new PdfPCell(new Phrase(iterator1.next().toString()));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);
                            }
                            else{
                                PdfPCell cell5 = new PdfPCell(new Phrase("-"));
                                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell5);
                            }
                        }
                    }

                }
                /*------Tasks----------*/
                else if(sec.matches("Tasks")) {

                    List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Tasks'");
                    for (Revenus r : revlist) {
                        if (r.getDate_rev().getMonthValue() == tf.getMonthValue()) {
                            listr.add(Long.toString(r.getSomme_rev()));
                            revsum=revsum+r.getSomme_rev();
                        }
                    }
                    List<Depenses> deplist = depenseService.selectBy("SECTEUR_DEP", "'Tasks'");
                    for (Depenses d : deplist) {
                        if (d.getDate_dep().getMonthValue() == tf.getMonthValue()) {
                            listdep.add(Long.toString(d.getSomme_dep()));
                            depsum=depsum+d.getSomme_dep();
                        }
                    }
                    int sd = listdep.size();
                    int sr= listr.size();
                    boolean test1=sd<=sr;
                    Iterator iterator = listdep.iterator();
                    Iterator iterator1 = listr.iterator();
                    if (test1) {
                        while (iterator1.hasNext()) {
                            PdfPCell cell2 = new PdfPCell(new Phrase(iterator1.next().toString()));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell2);
                            if (iterator.hasNext()) {
                                PdfPCell cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }
                            else {
                                PdfPCell cell3 = new PdfPCell(new Phrase("-"));
                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell3);
                            }

                        }
                    }
                    else {
                        while (iterator.hasNext()) {
                            PdfPCell cell3;
                            cell3 = new PdfPCell(new Phrase(iterator.next().toString()));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell3);
                            if(iterator1.hasNext()) {
                                PdfPCell cell4 = new PdfPCell(new Phrase(iterator1.next().toString()));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell4);
                            }
                            else{
                                PdfPCell cell5 = new PdfPCell(new Phrase("-"));
                                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(cell5);
                            }
                        }
                    }

                }

                /*-----------------------total------------*/

                PdfPCell cell6 = new PdfPCell(new Phrase(Long.toString(revsum)));
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell6);
                PdfPCell cell7 = new PdfPCell(new Phrase(Long.toString(depsum)));
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell7);

                /*-------------------------*/







                p3.add("Total\n");
                p4.add("\nContact : \n");
                p4.add("   number : "+m.getTel()+"\n");
                p4.add("   email : "+m.getEmail()+"\n");
                p4.add("   adress : "+m.getAdresse()+"\n");
                p4.add("   web site : "+m.getWebsite()+"\n");
                p4.add("\nSignature \n");

                document.add(p1);
                document.add(p2);
                document.add(p5);
                document.add(new LineSeparator());
                document.add(table);
                document.add(p3);

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
