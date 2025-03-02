package com.essensbestellung.csv;

import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.GruppenMitglieder;
import com.essensbestellung.entities.User;
import com.essensbestellung.entities.Location;
import com.essensbestellung.repository.IGruppenMitgliederRepository;
import com.essensbestellung.repository.IGruppenRepository;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.repository.IUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.List;


@Service
public class CsvImportService {

    private static final Logger logger = LoggerFactory.getLogger(CsvImportService.class);

    private final IUserRepository userRepository;
    private final CsvParser csvParser;
    private final ILocationRepository locationRepository;
    private final IGruppenRepository gruppenRepository;
    private final IGruppenMitgliederRepository gruppenMitgliederRepository;
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource; // Injecting DataSource to access connection pool

    @Autowired
    public CsvImportService(IUserRepository userRepository, ILocationRepository locationRepository,
                            IGruppenRepository gruppenRepository, IGruppenMitgliederRepository gruppenMitgliederRepository, CsvParser       csvParser, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.csvParser = csvParser;
        this.gruppenRepository = gruppenRepository;
        this.gruppenMitgliederRepository = gruppenMitgliederRepository;
        this.entityManager = entityManager;

    }


    @Transactional
    public void importUsersFromCsv(String filePath) {
        try {
            List<User> users = csvParser.parseCsvToUsers(filePath);

            for (User user : users) {

                try {
                    user.generateQRCode();
                    userRepository.save(user);
                    logger.info("Saved user to database: {}", user);
                } catch (Exception e) {
                    logger.error("Failed to save user: {}", user, e);
                }
            }

        } catch (IOException e) {
            logger.error("Error while parsing users from CSV", e);
        }
    }


    public void importLocationsFromCsv(String filePath) {
        try {
            //Nutzt csv Parser um Liste mit Locationszu erhalten
            List<Location> locations = csvParser.parseCsvToLocations(filePath);

            for (Location location : locations) {
                try {
                    //Einfuegung in Datenbank 
                    locationRepository.save(location);
                    logger.info("Saved location to database: {}", location);
                } catch (Exception e) {
                    logger.error("Failed to save location: ", e);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to save location: ", e);
        }
    }

    public void importGroupsFromCsv(String filePath) {
        try {

            refreshDatabaseConnection();


            List<Gruppen> groups = csvParser.parseCsvToGroups(filePath);
            for (Gruppen group : groups) {
                try {
                    gruppenRepository.save(group);
                    logger.info("Saved group to database: {}", group);
                } catch (Exception e) {
                    logger.error("Failed to save group: {}", group, e);
                }
            }

        } catch (IOException e) {
            logger.error("Error while parsing groups from CSV", e);
        }
    }
    public void importGroupMembersFromCsv(String filePath) {
        try {

            refreshDatabaseConnection();


            List<GruppenMitglieder> members = csvParser.parseCsvGruppenMitglieders(filePath);
            for (GruppenMitglieder member : members) {
                try {
                    gruppenMitgliederRepository.save(member);
                    logger.info("Saved member to database: {}", member);
                } catch (Exception e) {
                    logger.error("Failed to save member: {}", member, e);
                }
            }

        } catch (IOException e) {
            logger.error("Error while parsing group-members from CSV", e);
        }
    }

//Sicherstellung das Datenbankverbindung neu erstellt wird
    public void refreshDatabaseConnection() {
        try {

            if (dataSource instanceof HikariDataSource) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                hikariDataSource.getHikariPoolMXBean().softEvictConnections();
                logger.info("Evicted idle connections in the pool.");
            }

            entityManager.clear();
            logger.info("Cleared EntityManager session.");

        } catch (Exception e) {
            logger.error("Error refreshing database connection", e);
        }
    }
}