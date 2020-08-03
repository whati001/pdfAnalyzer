package com.rehka.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;

public class PdfFile {

    private final File file;
    private String title;
    private int pageCount;
    private int pageInColor;
    private PdfFileDim dimension;


    public PdfFile(File file) {
        this.file = file;
        this.title = "";
        this.pageCount = 0;
        this.pageInColor = 0;
        this.dimension = new PdfFileDim();
    }

    private static boolean usesColor(BufferedImage img) {
        for (int ix = 0; ix < img.getHeight(); ix++) {
            for (int iy = 0; iy < img.getWidth(); iy++) {
                int rgb = img.getRGB(iy, ix);
                int r = (0x00ff0000 & rgb) >> 16;
                int g = (0x0000ff00 & rgb) >> 8;
                int b = (0x000000ff & rgb);

                if (!(r == g && r == b && b == g)) {
                    return true;
                }
            }
        }
        return false;
    }

    public PdfFile analyze() {
        System.out.println(String.format("Start analysing File: %s", this.file));

        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(this.file)); PDDocument pdDoc = PDDocument.load(this.file)) {
            this.title = pdfDoc.getDocumentInfo().getTitle();
            this.pageCount = pdfDoc.getNumberOfPages();
            this.dimension.setX(pdfDoc.getDefaultPageSize().getHeight());
            this.dimension.setY(pdfDoc.getDefaultPageSize().getWidth());

            PDFRenderer pdfRenderer = new PDFRenderer(pdDoc);
            for (int idx = 0; idx < this.pageCount; ++idx) {
                if (usesColor(pdfRenderer.renderImage(idx))) {
                    this.pageInColor++;
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("Failed to analyze file: %s", this.file));
            e.printStackTrace();
            return null;
        } finally {
            System.out.println(String.format("Finished analyzing file: %s", this.file));
        }
        return this;
    }

    public File getFile() {
        return file;
    }

    public String getTitle() {
        return title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageInColor() {
        return pageInColor;
    }

    public int getPageInGray() {
        if (pageCount < pageInColor) return 0;
        return pageCount - pageInColor;
    }

    public PdfFileDim getDimension() {
        return dimension;
    }

    @Override
    public String toString() {
        return String.format("%s[file: %s, pageCount: %d, pageInColor: %d, dimension: %s]", this.getClass().getSimpleName(), this.file, this.pageCount, this.pageInColor, this.dimension.toString());
    }
}
