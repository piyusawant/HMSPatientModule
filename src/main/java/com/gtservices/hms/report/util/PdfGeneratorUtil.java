package com.gtservices.hms.report.util;

import com.gtservices.hms.report.entity.PatientReport;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfGeneratorUtil
{
    public static byte[] generateReport(PatientReport report)
    {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("HOSPITAL MEDICAL REPORT", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Report UID: " + report.getReportUid(), normalFont));
            document.add(new Paragraph("Report Type: " + report.getReportType(), normalFont));
            document.add(new Paragraph("Report Title: " + report.getReportTitle(), normalFont));
            document.add(new Paragraph("Generated At: " + report.getGeneratedAt(), normalFont));

            document.close();

            return out.toByteArray();

        }catch(Exception e)
        {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

}
