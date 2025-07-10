package io.joshuasalcedo.psgc.model.constants;

import io.joshuasalcedo.psgc.model.exception.PSGCException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides PSGC download URLs with validation.
 * Only URLs that are within valid date ranges AND have downloadable files are returned.
 */
public class PsgcDownloadUrl {

    private static final int MINIMUM_VALID_YEAR = 2025;
    
    /**
     * Gets all valid PSGC download URLs.
     * A URL is considered valid if:
     * 1. It's within the valid quarter/year scope (2025 onwards)
     * 2. The file is actually downloadable (HTTP 200 response)
     * 
     * @return List of FileURL objects containing valid download URLs
     * @throws PSGCException if an error occurs during URL generation or validation
     */
    public static List<FileURL> getPsgcDownloadUrls() throws PSGCException {
        List<FileURL> validFileUrls = new ArrayList<>();
        
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        
        // Calculate the last available quarter
        int lastAvailableQuarter = calculateAvailableQuarter(currentMonth);
        
        // Generate URLs starting from 2025
        for (int year = MINIMUM_VALID_YEAR; year <= currentYear; year++) {
            int maxQuarter = (year == currentYear) ? lastAvailableQuarter : 4;
            
            for (int quarter = 1; quarter <= maxQuarter; quarter++) {
                FileURL fileUrl = createAndValidateFileURL(year, quarter);
                if (fileUrl != null) {
                    validFileUrls.add(fileUrl);
                }
            }
        }
        
        return validFileUrls;
    }
    
    /**
     * Creates and validates a FileURL for a specific year and quarter.
     * Only returns the FileURL if all report types are accessible.
     */
    private static FileURL createAndValidateFileURL(int year, int quarter) {
        try {
            URL publicationUrl = PsgcDownloadUrlParser.generateURL(
                quarter, year, PsgcDownloadUrlParser.ReportType.PUBLICATION);
            URL summaryUrl = PsgcDownloadUrlParser.generateURL(
                quarter, year, PsgcDownloadUrlParser.ReportType.SUMMARY);
            URL summaryChangesUrl = PsgcDownloadUrlParser.generateURL(
                quarter, year, PsgcDownloadUrlParser.ReportType.SUMMARY_CHANGES);
            
            // Debug logging
            System.out.println("Checking " + year + " Q" + quarter + "...");
            
            // Check if all URLs are accessible
            boolean pubAccessible = PsgcDownloadUrlParser.isAccessible(publicationUrl);
            boolean sumAccessible = PsgcDownloadUrlParser.isAccessible(summaryUrl);
            boolean changeAccessible = PsgcDownloadUrlParser.isAccessible(summaryChangesUrl);
            
            System.out.println("  Publication: " + (pubAccessible ? "✓" : "✗") + " " + publicationUrl);
            System.out.println("  Summary: " + (sumAccessible ? "✓" : "✗") + " " + summaryUrl);
            System.out.println("  Changes: " + (changeAccessible ? "✓" : "✗") + " " + summaryChangesUrl);
            
            if (pubAccessible && sumAccessible && changeAccessible) {
                System.out.println("  All files accessible - adding to list");
                return new FileURL(publicationUrl, summaryUrl, summaryChangesUrl, year, quarter);
            } else {
                System.out.println("  Not all files accessible - skipping");
            }
        } catch (PSGCException e) {
            // Log error but continue processing other URLs
            System.err.println("Error creating URL for " + year + " Q" + quarter + ": " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Calculates the available quarter based on current month.
     * Q1 available after March, Q2 after June, Q3 after September, Q4 after December
     */
    private static int calculateAvailableQuarter(int currentMonth) {
        return Math.min((currentMonth - 1) / 3, 4);
    }
    
    /**
     * Internal parser class - completely hidden from external access
     */
    private static class PsgcDownloadUrlParser {
        private static final String BASE_URL = "https://psa.gov.ph/system/files/scd";
        private static final String URL_PATTERN = "%s/PSGC-%dQ-%d-%s";
        
        private enum ReportType {
            PUBLICATION("Publication-Datafile.xlsx"),
            SUMMARY("National-and-Provincial-Summary.xlsx"),
            SUMMARY_CHANGES("Summary-of-Changes.xlsx");
            
            private final String fileName;
            
            ReportType(String fileName) {
                this.fileName = fileName;
            }
        }
        
        private static URL generateURL(int quarter, int year, ReportType type) throws PSGCException {
            validateInputs(quarter, year);
            
            try {
                String urlString = String.format(URL_PATTERN, BASE_URL, quarter, year, type.fileName);
                return URI.create(urlString).toURL();
            } catch (MalformedURLException e) {
                throw new PSGCException("Failed to create URL", e);
            }
        }
        
        private static void validateInputs(int quarter, int year) throws PSGCException {
            if (quarter < 1 || quarter > 4) {
                throw new PSGCException("Invalid quarter: " + quarter);
            }
            if (year < MINIMUM_VALID_YEAR) {
                throw new PSGCException("Year must be " + MINIMUM_VALID_YEAR + " or later, but was: " + year);
            }
        }
        
        private static boolean isAccessible(URL url) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
                
                // Actually connect and try to download first few bytes
                connection.connect();
                int responseCode = connection.getResponseCode();
                
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Try to read first 4 bytes to verify it's a real file
                    try (InputStream is = connection.getInputStream()) {
                        byte[] buffer = new byte[4];
                        int bytesRead = is.read(buffer);
                        
                        // Excel files (.xlsx) start with "PK" (50 4B in hex)
                        boolean isValidFile = bytesRead >= 2 && buffer[0] == 0x50 && buffer[1] == 0x4B;
                        
                        System.out.println("    " + url.getFile().substring(url.getFile().lastIndexOf('/') + 1) + 
                                         " - Downloaded " + bytesRead + " bytes, Valid Excel: " + isValidFile);
                        
                        return isValidFile;
                    }
                } else {
                    System.out.println("    " + url.getFile().substring(url.getFile().lastIndexOf('/') + 1) + 
                                     " - HTTP " + responseCode + " (NOT FOUND)");
                    return false;
                }
                
            } catch (Exception e) {
                System.out.println("    " + url.getFile().substring(url.getFile().lastIndexOf('/') + 1) + 
                                 " - Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                return false;
            }
        }
    }
    
    /**
     * Immutable data class containing URLs for all report types for a specific quarter/year
     */
    public static class FileURL {
        private final URL publicationDownloadUrl;
        private final URL summaryDownloadUrl;
        private final URL summaryChangesDownloadUrl;
        private final int year;
        private final int quarter;
        
        private FileURL(URL publicationDownloadUrl, URL summaryDownloadUrl, 
                       URL summaryChangesDownloadUrl, int year, int quarter) {
            this.publicationDownloadUrl = publicationDownloadUrl;
            this.summaryDownloadUrl = summaryDownloadUrl;
            this.summaryChangesDownloadUrl = summaryChangesDownloadUrl;
            this.year = year;
            this.quarter = quarter;
        }
        
        public URL getPublicationDownloadUrl() {
            return publicationDownloadUrl;
        }
        
        public URL getSummaryDownloadUrl() {
            return summaryDownloadUrl;
        }
        
        public URL getSummaryChangesDownloadUrl() {
            return summaryChangesDownloadUrl;
        }
        
        public int getYear() {
            return year;
        }
        
        public int getQuarter() {
            return quarter;
        }
        
        @Override
        public String toString() {
            return String.format("FileURL{year=%d, quarter=Q%d}", year, quarter);
        }
    }
}