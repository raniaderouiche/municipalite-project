package org.fsb.municipalite.controllers;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

public class FinanceReportsPrintController {
    public static void downloadPDF(String year, Window window)   {
        double depAut=0L;
        double budAut=0L;
        double revAut=0L;
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
                p3.add("\n Generee le:"+ new Date());
                p3.setAlignment(Element.ALIGN_CENTER);
                p3.setFont(documentType);

                /*----------header of table------------*/
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
                PdfPCell cell5 = new PdfPCell(new Phrase("Authorization"));
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell5);

                List<Budget> budlista = budgetService.selectBy("SECTEUR", "'Authorization'");
                for (Budget b : budlista) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budAut=budAut+b.getBudget();
                    }
                }
                List<Depenses> deplista = depenseService.selectBy("SECTEUR_DEP", "'Authorization'");
                for (Depenses d : deplista) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depAut=depAut+d.getSomme_dep();
                    }
                }


                List<Revenus> revlista = revenuService.selectBy("SOURCE_REV", "'Authorization'");
                for (Revenus r : revlista) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revAut=revAut+r.getSomme_rev();
                    }
                }
                double ecartAut=(budAut+revAut)-depAut;

                PdfPCell cell6 = new PdfPCell(new Phrase(budAut+""));
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell6);
                PdfPCell cell7 = new PdfPCell(new Phrase(revAut+""));
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell7);
                PdfPCell cell8 = new PdfPCell(new Phrase(depAut+""));
                cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell8);
                PdfPCell cell9 = new PdfPCell(new Phrase(Double.toString(ecartAut)));
                cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell9);




                /*---------------*/
                /*----------Events----------*/
                PdfPCell cell10 = new PdfPCell(new Phrase("Events"));
                cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell10);

                List<Budget> budliste = budgetService.selectBy("SECTEUR", "'Events'");
                double budEvn=0L;
                for (Budget b : budliste) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budEvn=budEvn+b.getBudget();


                    }
                }
                List<Depenses> depliste = depenseService.selectBy("SECTEUR_DEP", "'Events'");
                double depEvn=0L;
                for (Depenses d : depliste) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depEvn=depEvn+d.getSomme_dep();


                    }
                }


                List<Revenus> revliste = revenuService.selectBy("SOURCE_REV", "'Events'");
                double revEvn=0L;
                for (Revenus r : revliste) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revEvn=revEvn+r.getSomme_rev();


                    }
                }
                double ecartEvn=(budEvn+revEvn)-depEvn;

                PdfPCell cell11 = new PdfPCell(new Phrase(budEvn+""));
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell11);
                PdfPCell cell12 = new PdfPCell(new Phrase(revEvn+""));
                cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell12);
                PdfPCell cell13 = new PdfPCell(new Phrase(depEvn+""));
                cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell13);
                PdfPCell cell14 = new PdfPCell(new Phrase(Double.toString(ecartEvn)));
                cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell14);
                /*----------------*/
                /*------------Complaints--------------*/
                PdfPCell cell15 = new PdfPCell(new Phrase("Complaints"));
                cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell15);

                List<Budget> budlistc = budgetService.selectBy("SECTEUR", "'Complaints'");
                double budcom=0L;
                for (Budget b : budlistc) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budcom=budcom+b.getBudget();

                    }
                }
                List<Depenses> deplistc = depenseService.selectBy("SECTEUR_DEP", "'Complaints'");
                double depcom=0L;
                for (Depenses d : deplistc) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depcom=depcom+d.getSomme_dep();

                    }
                }

                List<Revenus> revlistc = revenuService.selectBy("SOURCE_REV", "'Complaints'");
                double revcom=0L;
                for (Revenus r : revlistc) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revcom=revcom+r.getSomme_rev();
                    }
                }
                double ecartcom=(budcom+revcom)-depcom;

                PdfPCell cell16 = new PdfPCell(new Phrase(budcom+""));
                cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell6);
                PdfPCell cell17 = new PdfPCell(new Phrase(revcom+""));
                cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell17);
                PdfPCell cell18 = new PdfPCell(new Phrase(depcom+""));
                cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell18);
                PdfPCell cell19 = new PdfPCell(new Phrase(Double.toString(ecartcom)));
                cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell19);
                /*----------------------*/
                /*-----------projects-------------*/
                PdfPCell cell20 = new PdfPCell(new Phrase("Projects"));
                cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell20);

                List<Budget> budlistp = budgetService.selectBy("SECTEUR", "'Projects'");
                double budPro=0L;
                for (Budget b : budlistp) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budPro=budPro+b.getBudget();


                    }
                }
                List<Depenses> deplistp = depenseService.selectBy("SECTEUR_DEP", "'Projects'");
                double depPro=0L;
                for (Depenses d : deplistp) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depPro=depPro+d.getSomme_dep();

                    }
                }
                List<Revenus> revlistp = revenuService.selectBy("SOURCE_REV", "'Projects'");
                double revPro=0L;
                for (Revenus r : revlistp) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revPro=revPro+r.getSomme_rev();

                    }
                }
                double ecartPro=(budPro+revPro)-depPro;

                PdfPCell cell21 = new PdfPCell(new Phrase(budPro+""));
                cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell21);
                PdfPCell cell22 = new PdfPCell(new Phrase(revPro+""));
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell22);
                PdfPCell cell23 = new PdfPCell(new Phrase(depPro+""));
                cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell23);
                PdfPCell cell24 = new PdfPCell(new Phrase(Double.toString(ecartPro)));
                cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell24);
                /*---------------------------*/
                /*----------Materiel-----------*/
                PdfPCell cell25 = new PdfPCell(new Phrase("Tools"));
                cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell25);

                List<Budget> budlistm = budgetService.selectBy("SECTEUR", "'Tools'");
                double budM=0L;
                for (Budget b : budlistm) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budM=budM+b.getBudget();

                    }
                }
                List<Depenses> deplistm = depenseService.selectBy("SECTEUR_DEP", "'Tools'");
                double depM=0L;
                for (Depenses d : deplistm) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depM=depM+d.getSomme_dep();

                    }
                }

                List<Revenus> revlistm = revenuService.selectBy("SOURCE_REV", "'Tools'");
                double revM=0L;
                for (Revenus r : revlistm) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revM=revM+r.getSomme_rev();


                    }
                }
                double ecartM=(budM+revM)-depM;

                PdfPCell cell26 = new PdfPCell(new Phrase(budM+""));
                cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell26);
                PdfPCell cell27 = new PdfPCell(new Phrase(revM+""));
                cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell27);
                PdfPCell cell28 = new PdfPCell(new Phrase(depM+""));
                cell28.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell28);
                PdfPCell cell29 = new PdfPCell(new Phrase(Double.toString(ecartM)));
                cell29.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell29);
                /*-------------------------*/
                /*---------------teams-------------*/
                PdfPCell cell30 = new PdfPCell(new Phrase("Teams"));
                cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell30);

                List<Budget> budlistt = budgetService.selectBy("SECTEUR", "'Teams'");
                double budT=0L;
                for (Budget b : budlistt) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budT=budT+b.getBudget();


                    }
                }
                List<Depenses> deplistt = depenseService.selectBy("SECTEUR_DEP", "'Teams'");
                double depT=0L;
                for (Depenses d : deplistt) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        depT=depT+d.getSomme_dep();


                    }
                }


                List<Revenus> revlistt = revenuService.selectBy("SOURCE_REV", "'Teams'");
                double revT=0L;
                for (Revenus r : revlistt) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revT=revT+r.getSomme_rev();


                    }
                }
                double ecartT=(budT+revT)-depT;

                PdfPCell cell31 = new PdfPCell(new Phrase(budT+""));
                cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell31);
                PdfPCell cell32 = new PdfPCell(new Phrase(revT+""));
                cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell32);
                PdfPCell cell33 = new PdfPCell(new Phrase(depT+""));
                cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell33);
                PdfPCell cell34 = new PdfPCell(new Phrase(Double.toString(ecartT)));
                cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell34);
                /*----------------------*/
                /*------------tasks--------------*/
                PdfPCell cell35 = new PdfPCell(new Phrase("Tasks"));
                cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell35);

                List<Budget> budlisttas = budgetService.selectBy("SECTEUR", "'Tasks'");
                double budtas=0L;
                for (Budget b : budlisttas) {
                    if (b.getYear().getYear() == Integer.parseInt(year)) {
                        budtas=budtas+b.getBudget();


                    }
                }
                List<Depenses> deplisttas = depenseService.selectBy("SECTEUR_DEP", "'Tasks'");
                double deptas=0L;
                for (Depenses d : deplisttas) {
                    if (d.getDate_dep().getYear() == Integer.parseInt(year)) {
                        deptas=deptas+d.getSomme_dep();


                    }
                }


                List<Revenus> revlisttas = revenuService.selectBy("SOURCE_REV", "'Tasks'");
                double revtas=0L;
                for (Revenus r : revlisttas) {
                    if (r.getDate_rev().getYear() == Integer.parseInt(year)) {
                        revtas=revtas+r.getSomme_rev();


                    }
                }
                double ecarttas=(budtas+revtas)-deptas;

                PdfPCell cell36 = new PdfPCell(new Phrase(budtas+""));
                cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell36);
                PdfPCell cell37 = new PdfPCell(new Phrase(revtas+""));
                cell37.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell37);
                PdfPCell cell38 = new PdfPCell(new Phrase(deptas+""));
                cell38.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell38);
                PdfPCell cell39 = new PdfPCell(new Phrase(Double.toString(ecarttas)));
                cell39.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell39);
                /*-------------------------*/


                /*-----------------Total-------------*/
                double budtot=budAut+budEvn+budtas+budcom+budPro+budT+budM;
                double revtot=revAut+revcom+revtas+revEvn+revPro+revT+revM;
                double deptot=depAut+deptas+depcom+depEvn+depPro+depM+depT;
                double ecarttot=ecartAut+ecartcom+ecartEvn+ecarttas+ecartPro+ecartM+ecartT;
                PdfPCell cell40 = new PdfPCell(new Phrase("Total"));
                cell40.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell40);
                PdfPCell cell41 = new PdfPCell(new Phrase(Double.toString(budtot)));
                cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell41);
                PdfPCell cell42 = new PdfPCell(new Phrase(Double.toString(revtot)));
                cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell42);
                PdfPCell cell43 = new PdfPCell(new Phrase(Double.toString(deptot)));
                cell43.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell43);
                PdfPCell cell44 = new PdfPCell(new Phrase(Double.toString(ecarttot)));
                cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell44);

                /*----------------*/
                /*---------logo------------*/
                Image img = Image.getInstance("src/main/resources/assets/img/Coat_of_arms.png");
                //maintaining aspect ratio
                float imgW = 64;
                float imgH = 101;
                float padding = 15;
                img.setAbsolutePosition(padding,document.getPageSize().getHeight() - (imgH+padding));
                img.scaleAbsolute(imgW,imgH);

                /*------end second page---*/

                /*--footer----*/
                p4.add("\nContact : \n");
                p4.add("   number : "+m.getTel()+"\n");
                p4.add("   email : "+m.getEmail()+"\n");
                p4.add("   adress : "+m.getAdresse()+"\n");
                p4.add("   web site : "+m.getWebsite()+"\n");
                p4.add("\nSignature \n");
                p4.setAlignment(Element.ALIGN_BOTTOM);
                /*--end footer----*/
                document.add(img);
                document.add(p1);
                document.add(p2);
                document.add(p3);
                document.add(new LineSeparator());
                document.add(table);
                document.add(new LineSeparator());
                document.add(p4);
                /*------sencond page--------------*/
                document.newPage();
                document.add(img);

                /*---------budget pie chart----------*/
                DefaultPieDataset dataSetB = new DefaultPieDataset();
                dataSetB.setValue("Authorizations", budAut);
                dataSetB.setValue("Complaints", budcom);
                dataSetB.setValue("Teams", budT);
                dataSetB.setValue("Tasks", budtas);
                dataSetB.setValue("Tools", budM);
                dataSetB.setValue("Projects", budPro);
                dataSetB.setValue("Events", budEvn);

                JFreeChart chartB = ChartFactory.createPieChart(
                        "Budgets per Sectors", dataSetB, true, true, false);


                PdfContentByte contentByteB = writer.getDirectContent();
                float width=350;
                float height=250;
                PdfTemplate templateB = contentByteB.createTemplate(width, height);
                Graphics2D graphics2dB = templateB.createGraphics(width, height,
                        new DefaultFontMapper());
                Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                        height);

                chartB.draw(graphics2dB, rectangle2d);

                graphics2dB.dispose();
                contentByteB.addTemplate(templateB, 100, 550);
                /*-------------*/
                /*---depenses pie chart------------*/
                DefaultPieDataset dataSetD = new DefaultPieDataset();
                dataSetD.setValue("Authorizations", depAut);
                dataSetD.setValue("Complaints", depcom);
                dataSetD.setValue("Teams", depT);
                dataSetD.setValue("Tasks", deptas);
                dataSetD.setValue("Tools", depM);
                dataSetD.setValue("Projects", depPro);
                dataSetD.setValue("Events", depEvn);

                JFreeChart chartD = ChartFactory.createPieChart(
                        "Expenses per Sectors", dataSetD, true, true, false);


                PdfContentByte contentByteD = writer.getDirectContent();

                PdfTemplate templateD = contentByteD.createTemplate(width, height);
                Graphics2D graphics2dD = templateD.createGraphics(width, height,
                        new DefaultFontMapper());
                Rectangle2D rectangle2dD = new Rectangle2D.Double(0, 0, width,
                        height);

                chartD.draw(graphics2dD, rectangle2dD);

                graphics2dD.dispose();
                contentByteD.addTemplate(templateD, 100, 270);
                /*--------------*/
                /*---------revenus pie chart-----------*/
                DefaultPieDataset dataSetR = new DefaultPieDataset();
                dataSetR.setValue("Authorizations", revAut);
                dataSetR.setValue("Complaints", revcom);
                dataSetR.setValue("Teams", revT);
                dataSetR.setValue("Tasks", revtas);
                dataSetR.setValue("Tools", revM);
                dataSetR.setValue("Projects", revPro);
                dataSetR.setValue("Events", revEvn);

                JFreeChart chartR = ChartFactory.createPieChart(
                        "Incomes per Sectors", dataSetR, true, true, false);


                PdfContentByte contentByteR = writer.getDirectContent();

                PdfTemplate templateR = contentByteR.createTemplate(width, height);
                Graphics2D graphics2dR = templateR.createGraphics(width, height,
                        new DefaultFontMapper());
                Rectangle2D rectangle2dR = new Rectangle2D.Double(0, 0, width,
                        height);

                chartR.draw(graphics2dR, rectangle2dR);

                graphics2dR.dispose();
                contentByteR.addTemplate(templateR, 100, 20);
                /*---------------------*/
                /*--- end second page-----*/
                /*------third page--------------*/
                document.newPage();
                document.add(img);
                document.add(new LineSeparator());
                /*-----profit chart----------*/
                DefaultCategoryDataset dataSetP = new DefaultCategoryDataset();
                dataSetP.setValue(ecartAut, "Profits", "Authorisations");
                dataSetP.setValue(ecartcom, "Profits", "Complaints");
                dataSetP.setValue(ecartT, "Profits", "Teams");
                dataSetP.setValue(ecartM, "Profits", "Tools");
                dataSetP.setValue(ecartPro, "Profits", "Projects");
                dataSetP.setValue(ecartEvn, "Profits", "Events");
                dataSetP.setValue(ecarttas, "Profits", "Tasks");

                JFreeChart chartP = ChartFactory.createBarChart(
                        "Yearly profits", "Sectors", "Profit in DTN",
                        dataSetP, PlotOrientation.VERTICAL, false, true, false);
                PdfContentByte contentByteP = writer.getDirectContent();
                float widthP=550;
                PdfTemplate templateP = contentByteP.createTemplate(widthP, height);
                Graphics2D graphics2dP = templateP.createGraphics(widthP, height,
                        new DefaultFontMapper());
                Rectangle2D rectangle2dP = new Rectangle2D.Double(0, 0, widthP,
                        height);

                chartP.draw(graphics2dP, rectangle2dP);

                graphics2dP.dispose();
                contentByteP.addTemplate(templateP, 20, 300);
                /*-----------------*/

                document.add(new LineSeparator());
                document.close();
                writer.close();


            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("itext PDF program executed");
    }
}
