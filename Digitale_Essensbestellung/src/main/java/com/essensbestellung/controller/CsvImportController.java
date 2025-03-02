package com.essensbestellung.controller;

import com.essensbestellung.csv.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class CsvImportController {

    @Autowired
    private CsvImportService csvImportService;

    @PostMapping("/import-users")
    public String importUsers(@RequestParam("file") MultipartFile file) {
        try {
            // Create a temporary file to save the uploaded CSV content
            File tempFile = File.createTempFile("users", ".csv");
            file.transferTo(tempFile);

            // Import Users
            csvImportService.importUsersFromCsv(tempFile.getAbsolutePath());

            // Import Locations
            csvImportService.importLocationsFromCsv(tempFile.getAbsolutePath());

            // Import Groups
            csvImportService.importGroupsFromCsv(tempFile.getAbsolutePath());

            // Import Group Members
            csvImportService.importGroupMembersFromCsv(tempFile.getAbsolutePath());

            // If all imports are successful
            return "Users, Locations, Groups, and Group Members imported successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to import data: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected error: " + e.getMessage();
        }
    }
}