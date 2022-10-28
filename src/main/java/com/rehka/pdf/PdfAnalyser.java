package com.rehka.pdf;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PdfAnalyser {

    private static final float priceInColor = 0.29f;
    private static final float priceInGray = 0.16f;

    private static int totalFileCount = 0;
    private static int totalFailed = 0;
    private static int totalPageCount = 0;
    private static int totalPageInColor = 0;
    private static int totalPageInGray = 0;
    private static float totalPrice = 0f;
    private static HashMap<String, Integer> totalDims = new HashMap<>();

    private static File getDefaultDir() {
        File home = new File(System.getProperty("user.home"));
        File defHome = new File(home, "Desktop/PDF");
        if (defHome.exists()) {
            return defHome;
        }
        return home;
    }

    private static File requestDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.setCurrentDirectory(getDefaultDir());
        f.showSaveDialog(null);
        return f.getSelectedFile();
    }

    private static File[] listDirectory(String mask) {
        File dir = requestDirectory();
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            System.out.println("Please choose a proper directory");
            System.exit(-1);
        }
        return dir.listFiles((new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.matches(mask);
            }
        }));
    }

    public static void main(String[] args) {
        System.out.println("PdfAnalyzer started, please select a folder containing the PDF files");
        List<PdfFile> res = Arrays.stream(listDirectory(".*\\.pdf$")).parallel()
                .map(PdfFile::new)
                .map(PdfFile::analyze).collect(toList());

        for (PdfFile f : res) {
            if (f != null) {
                System.out.println((PdfFile) f);
                totalFileCount++;
                totalPageCount += f.getPageCount();
                totalPageInColor += f.getPageInColor();
                totalPageInGray += f.getPageInGray();
                totalPrice += f.getPageInColor() * priceInColor + f.getPageInGray() * priceInGray;
                if (totalDims.containsKey(f.getDimension().getKey())) {
                    totalDims.put(f.getDimension().getKey(), totalDims.get(f.getDimension().getKey()) + 1);
                } else {
                    totalDims.put(f.getDimension().getKey(), 1);
                }
            } else {
                totalFailed++;
            }
        }

        System.out.println("################################################################################");
        ArrayList<String> result = new ArrayList<>();
        result.add(String.format("Total files to print: %d", totalFileCount));
        result.add(String.format("Total pages to print: %d", totalPageCount));
        result.add(String.format("Total pages in color to print: %d", totalPageInColor));
        result.add(String.format("Total pages in gray to print: %d", totalPageInGray));
        result.add(String.format("Total dimensions to print: %s", totalDims));
        result.add(String.format("Total price for print job: %.2f", totalPrice));
        if (totalFailed > 0) {
            result.add(String.format("################################################################################"));
            result.add(String.format("Total count of failed analyzed files: %d", totalFailed));
        }

        System.out.println(String.join("\n", result));
        JOptionPane.showMessageDialog(null, String.join("\n", result));
    }
}
