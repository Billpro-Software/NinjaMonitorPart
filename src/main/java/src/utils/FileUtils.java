package src.utils;

import src.enums.ErrorType;
import src.exceptions.ApplicationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class FileUtils {
    

    public static String loadFileToString(String fileName) {

        String strFile = "";

        String newLine = "\n";

        //PrintUtils.printHeader("FileUtils.loadFileToString : <" + fileName + ">");

        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null) {
                strFile = strFile + str + newLine;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
/*
        for (String line : list) {
            System.out.println(line.trim());
        }
  */
        return strFile;

    }


    public static String getTimeStampForFileName() {

        // time stamp for file name, that will insure each file get unique name
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        Date date = new Date();
        return (dateFormat.format(date));

    }

    public static String PROPERTIES_FILE_EXTENSION = "properties";

    public static void printFile(String fileName) throws Exception {

        System.out.println("FileUtils :  fileName : <" + fileName + ">");

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        int oneByte;
        while ((oneByte = fis.read()) != -1) {
            System.out.write(oneByte);
            // System.out.print((char)oneByte); // could also do this
        }
        System.out.flush();
    }

    public static String getFileExtensionWithDot(String fileName) throws Exception {

        String fileExtension = getFileExtension(fileName);

        if (StringUtilsNinjaAutomatedTest.isNullOrEmpty(fileExtension)) {
            return "";
        }

        fileExtension = "." + fileExtension;
        System.out.println("FileUtils :  getFileExtensionWithDot : <" + fileName + ">");
        System.out.println("FileUtils :  getFileExtensionWithDot : <" + fileExtension + ">");
        return fileExtension;
    }

    public static String getFileExtension(String fileName) throws Exception {
        String fileExtension = "";

        int i = fileName.lastIndexOf('.');

        if (i > 0) {
            fileExtension = fileName.substring(i + 1);
        }
        System.out.println("FileUtils :  getFileExtension : <" + fileName + ">");
        System.out.println("FileUtils :  getFileExtension : <" + fileExtension + ">");

        return fileExtension;
    }

    public static Properties loadProperties(String fileName) {
        System.out.println("FileUtils :  loadProperties : <" + fileName + ">");
        try {
            printFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(fileName);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nFileUtils :  loadProperties :  properties<" + properties + ">");


        return (properties);
    }

    public static boolean isPropertiesFileExtension(String fileName) throws Exception {

        boolean result = false;

        if (PROPERTIES_FILE_EXTENSION.equals(getFileExtension(fileName))) {
            result = true;
        }

        System.out.println("FileUtils :  isPropertiesFileExtension : <" + result + ">");

        return result;
    }


}


