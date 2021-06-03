package org.fsb.municipalite.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.fsb.municipalite.entities.Budget;
import org.fsb.municipalite.entities.Depenses;
import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.entities.Revenus;
import org.fsb.municipalite.services.impl.BudgetServiceImpl;
import org.fsb.municipalite.services.impl.DepensesServiceImpl;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;
import org.fsb.municipalite.services.impl.RevenusServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

public class FinanceReportsPrintController {
    public static void downloadPDF(String year, Window window) {
        Long depAut=0L;
        Long budAut=0L;
        Long revAut=0L;
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


                p1.add("Tunisian republic\n"+m.getNom());
                p1.setAlignment(Element.ALIGN_CENTER);
                p1.setFont(municipalityName);


                p2.add("\nRapport Financiers \n ======================================================");
                p2.setAlignment(Element.ALIGN_CENTER);
                p2.setFont(documentType);
                p3.add("\n Generee le:"+ new Date().toString());
                p3.setAlignment(Element.ALIGN_CENTER);
                p3.setFont(documentType);


                /*----------header------------*/
                PdfPTable table = new PdfPTable(5);
                PdfPCell cell = new PdfPCell(new Phrase("Secteurs"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                PdfPCell cell1 = new PdfPCell(new Phrase("Budgets"));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Phrase("Total Revenus"));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);
                PdfPCell cell3 = new PdfPCell(new Phrase("Total Depenses"));
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell3);
                PdfPCell cell4 = new PdfPCell(new Phrase("Ecart"));
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell4);
                /*-------------*/
                DepensesServiceImpl depenseService = new DepensesServiceImpl();
                RevenusServiceImpl revenuService = new RevenusServiceImpl();
                BudgetServiceImpl budgetService = new BudgetServiceImpl();

                /*-----------------*/

                /*-----authorisation--------*/
                PdfPCell cell5 = new PdfPCell(new Phrase("Authorisation"));
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell5);

                List<Budget> budlista = budgetService.selectBy("SECTEUR", "'Authorisation'");
                for (Budget b : budlista) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budAut=budAut+b.getBudget();


                    }
                }
                List<Depenses> deplista = depenseService.selectBy("SECTEUR_DEP", "'Authorisation'");
                for (Depenses d : deplista) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depAut=depAut+d.getSomme_dep();


                    }
                }


                List<Revenus> revlista = revenuService.selectBy("SOURCE_REV", "'Authorisation'");
                for (Revenus r : revlista) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revAut=revAut+r.getSomme_rev();


                    }
                }
                long ecartAut=(budAut+revAut)-depAut;

                PdfPCell cell6 = new PdfPCell(new Phrase(budAut.toString()));
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell6);
                PdfPCell cell7 = new PdfPCell(new Phrase(revAut.toString()));
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell7);
                PdfPCell cell8 = new PdfPCell(new Phrase(depAut.toString()));
                cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell8);
                PdfPCell cell9 = new PdfPCell(new Phrase(Long.toString(ecartAut)));
                cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell9);




                /*---------------*/
                /*----------Events----------*/
                PdfPCell cell10 = new PdfPCell(new Phrase("Events"));
                cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell10);

                List<Budget> budliste = budgetService.selectBy("SECTEUR", "'Events'");
                Long budEvn=0L;
                for (Budget b : budliste) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budEvn=budEvn+b.getBudget();


                    }
                }
                List<Depenses> depliste = depenseService.selectBy("SECTEUR_DEP", "'Events'");
                Long depEvn=0L;
                for (Depenses d : depliste) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depEvn=depEvn+d.getSomme_dep();


                    }
                }


                List<Revenus> revliste = revenuService.selectBy("SOURCE_REV", "'Events'");
                Long revEvn=0L;
                for (Revenus r : revliste) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revEvn=revEvn+r.getSomme_rev();


                    }
                }
                long ecartEvn=(budEvn+revEvn)-depEvn;

                PdfPCell cell11 = new PdfPCell(new Phrase(budEvn.toString()));
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell11);
                PdfPCell cell12 = new PdfPCell(new Phrase(revEvn.toString()));
                cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell12);
                PdfPCell cell13 = new PdfPCell(new Phrase(depEvn.toString()));
                cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell13);
                PdfPCell cell14 = new PdfPCell(new Phrase(Long.toString(ecartEvn)));
                cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell14);
                /*----------------*/
                /*------------Complaints--------------*/
                PdfPCell cell15 = new PdfPCell(new Phrase("Complaints"));
                cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell15);

                List<Budget> budlistc = budgetService.selectBy("SECTEUR", "'Complaints'");
                Long budcom=0L;
                for (Budget b : budlistc) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budcom=budcom+b.getBudget();


                    }
                }
                List<Depenses> deplistc = depenseService.selectBy("SECTEUR_DEP", "'Complaints'");
                Long depcom=0L;
                for (Depenses d : deplistc) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depcom=depcom+d.getSomme_dep();


                    }
                }


                List<Revenus> revlistc = revenuService.selectBy("SOURCE_REV", "'Authorisation'");
                Long revcom=0L;
                for (Revenus r : revlistc) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revcom=revcom+r.getSomme_rev();


                    }
                }
                long ecartcom=(budcom+revcom)-depcom;

                PdfPCell cell16 = new PdfPCell(new Phrase(budcom.toString()));
                cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell6);
                PdfPCell cell17 = new PdfPCell(new Phrase(revcom.toString()));
                cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell17);
                PdfPCell cell18 = new PdfPCell(new Phrase(depcom.toString()));
                cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell18);
                PdfPCell cell19 = new PdfPCell(new Phrase(Long.toString(ecartcom)));
                cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell19);
                /*----------------------*/
                /*-----------projects-------------*/
                PdfPCell cell20 = new PdfPCell(new Phrase("Projets"));
                cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell20);

                List<Budget> budlistp = budgetService.selectBy("SECTEUR", "'Projets'");
                Long budPro=0L;
                for (Budget b : budlistp) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budPro=budPro+b.getBudget();


                    }
                }
                List<Depenses> deplistp = depenseService.selectBy("SECTEUR_DEP", "'Projets'");
                Long depPro=0L;
                for (Depenses d : deplistp) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depPro=depPro+d.getSomme_dep();


                    }
                }


                List<Revenus> revlistp = revenuService.selectBy("SOURCE_REV", "'Authorisation'");
                Long revPro=0L;
                for (Revenus r : revlistp) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revPro=revPro+r.getSomme_rev();


                    }
                }
                long ecartPro=(budPro+revPro)-depPro;

                PdfPCell cell21 = new PdfPCell(new Phrase(budPro.toString()));
                cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell21);
                PdfPCell cell22 = new PdfPCell(new Phrase(revPro.toString()));
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell22);
                PdfPCell cell23 = new PdfPCell(new Phrase(depPro.toString()));
                cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell23);
                PdfPCell cell24 = new PdfPCell(new Phrase(Long.toString(ecartPro)));
                cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell24);
                /*---------------------------*/
                /*----------Materiel-----------*/
                PdfPCell cell25 = new PdfPCell(new Phrase("Materiel"));
                cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell25);

                List<Budget> budlistm = budgetService.selectBy("SECTEUR", "'Materiel'");
                Long budM=0L;
                for (Budget b : budlistm) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budM=budM+b.getBudget();


                    }
                }
                List<Depenses> deplistm = depenseService.selectBy("SECTEUR_DEP", "'Materiel'");
                Long depM=0L;
                for (Depenses d : deplistm) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depM=depM+d.getSomme_dep();


                    }
                }


                List<Revenus> revlistm = revenuService.selectBy("SOURCE_REV", "'Materiel'");
                Long revM=0L;
                for (Revenus r : revlistm) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revM=revM+r.getSomme_rev();


                    }
                }
                long ecartM=(budM+revM)-depM;

                PdfPCell cell26 = new PdfPCell(new Phrase(budM.toString()));
                cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell26);
                PdfPCell cell27 = new PdfPCell(new Phrase(revM.toString()));
                cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell27);
                PdfPCell cell28 = new PdfPCell(new Phrase(depM.toString()));
                cell28.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell28);
                PdfPCell cell29 = new PdfPCell(new Phrase(Long.toString(ecartM)));
                cell29.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell29);
                /*-------------------------*/
                /*---------------teams-------------*/
                PdfPCell cell30 = new PdfPCell(new Phrase("Teams"));
                cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell30);

                    List<Budget> budlistt = budgetService.selectBy("SECTEUR", "'Teams'");
                Long budT=0L;
                for (Budget b : budlistt) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budT=budT+b.getBudget();


                    }
                }
                List<Depenses> deplistt = depenseService.selectBy("SECTEUR_DEP", "'Teams'");
                Long depT=0L;
                for (Depenses d : deplistt) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depT=depT+d.getSomme_dep();


                    }
                }


                List<Revenus> revlistt = revenuService.selectBy("SOURCE_REV", "'Teams'");
                Long revT=0L;
                for (Revenus r : revlistt) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revT=revT+r.getSomme_rev();


                    }
                }
                long ecartT=(budT+revT)-depT;

                PdfPCell cell31 = new PdfPCell(new Phrase(budT.toString()));
                cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell31);
                PdfPCell cell32 = new PdfPCell(new Phrase(revT.toString()));
                cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell32);
                PdfPCell cell33 = new PdfPCell(new Phrase(depT.toString()));
                cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell33);
                PdfPCell cell34 = new PdfPCell(new Phrase(Long.toString(ecartT)));
                cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell34);
                /*----------------------*/
                /*------------tasks--------------*/
                PdfPCell cell35 = new PdfPCell(new Phrase("Tasks"));
                cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell35);

                List<Budget> budlisttas = budgetService.selectBy("SECTEUR", "'Tasks'");
                Long budtas=0L;
                for (Budget b : budlisttas) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budtas=budtas+b.getBudget();


                    }
                }
                List<Depenses> deplisttas = depenseService.selectBy("SECTEUR_DEP", "'Tasks'");
                Long deptas=0L;
                for (Depenses d : deplisttas) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        deptas=deptas+d.getSomme_dep();


                    }
                }


                List<Revenus> revlisttas = revenuService.selectBy("SOURCE_REV", "'Tasks'");
                Long revtas=0L;
                for (Revenus r : revlisttas) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revtas=revtas+r.getSomme_rev();


                    }
                }
                long ecarttas=(budtas+revtas)-deptas;

                PdfPCell cell36 = new PdfPCell(new Phrase(budtas.toString()));
                cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell36);
                PdfPCell cell37 = new PdfPCell(new Phrase(revtas.toString()));
                cell37.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell37);
                PdfPCell cell38 = new PdfPCell(new Phrase(deptas.toString()));
                cell38.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell38);
                PdfPCell cell39 = new PdfPCell(new Phrase(Long.toString(ecarttas)));
                cell39.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell39);
                /*-------------------------*/


                /*-----------------Total-------------*/
                long budtot=budAut+budEvn+budtas+budcom+budPro+budT+budM;
                long revtot=revAut+revcom+revtas+revEvn+revPro+revT+revM;
                long deptot=depAut+deptas+depcom+depEvn+depPro+depM+depT;
                long ecarttot=ecartAut+ecartcom+ecartEvn+ecarttas+ecartPro+ecartM+ecartT;
                PdfPCell cell40 = new PdfPCell(new Phrase("Total"));
                cell40.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell40);
                PdfPCell cell41 = new PdfPCell(new Phrase(Long.toString(budtot)));
                cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell41);
                PdfPCell cell42 = new PdfPCell(new Phrase(Long.toString(revtot)));
                cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell42);
                PdfPCell cell43 = new PdfPCell(new Phrase(Long.toString(deptot)));
                cell43.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell43);
                PdfPCell cell44 = new PdfPCell(new Phrase(Long.toString(ecarttot)));
                cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell44);

                /*----------------*/


                p4.add("\nContact : \n");
                p4.add("   number : "+m.getTel()+"\n");
                p4.add("   email : "+m.getEmail()+"\n");
                p4.add("   adress : "+m.getAdresse()+"\n");
                p4.add("   web site : "+m.getWebsite()+"\n");
                p4.add("\nSignature \n");

                document.add(p1);
                document.add(p2);
                document.add(p3);
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
