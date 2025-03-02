package com.essensbestellung.csv;


import com.essensbestellung.entities.GroupMemberId;
import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.GruppenMitglieder;
import com.essensbestellung.entities.Location;
import com.essensbestellung.entities.User;
import com.essensbestellung.enums.Role;
import com.essensbestellung.repository.IGruppenRepository;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.repository.IUserRepository;
import com.essensbestellung.utils.GermanCharacterNormalizer;
import com.essensbestellung.utils.LocationNameExtractor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

@Component
public class CsvParser {

    private final ILocationRepository locationRepository;
    private final IUserRepository userRepository;
    private final IGruppenRepository gruppenRepository;
    

    public CsvParser(ILocationRepository locationRepository,IUserRepository userRepository,IGruppenRepository gruppenRepository ) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.gruppenRepository = gruppenRepository;

    }
//Parser um Standorte einzufügen
    public List<Location> parseCsvToLocations(String filePath) throws IOException {
        //fuegt grundsaetzlich alle Locations als Strings ein
        List<String[]> rawData = new ArrayList<>();
        List<Location> locations = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(";");
                if (values.length >= 6) {
                    rawData.add(values);
                } else {
                    System.err.println("Invalid row: " + line);
                }
            }
        }

/*Sortierung und Sicherstellung, dass nur unique names als Location eingefügt werden
Import s.o.
*/
        List<String> uniqueNames = LocationNameExtractor.extractUniqueLocationNames(rawData);

//bringt die Locations in eine List 
        for (String name : uniqueNames) {
            Location location = new Location();
            location.setLocationName(name);
            locations.add(location);
        }

        return locations;
    }


    public List<User> parseCsvToUsers(String filePath) throws IOException {
        List<User> users = new ArrayList<>();


        Charset encoding = StandardCharsets.UTF_8;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }


                String[] values = line.split(";");

                if (values.length < 6) {
                    System.err.println("Invalid data row: " + line);
                    continue;
                }


                String userName = values[1].trim();
                String gruppe1 = values[4].trim();


                gruppe1 = GermanCharacterNormalizer.normalize(gruppe1);


                String pattern = ".*(kueche|hauswirtschaft).*";
                Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                Matcher matcher = compiledPattern.matcher(gruppe1);


                Role role = matcher.matches() ? Role.KUECHENPERSONAL : Role.KUNDE;


                User user = new User();
                user.setUsername(userName);
                user.setFullname(userName);
                user.setRole(role);
                user.setPassword("passwort123");


                users.add(user);
            }
        }
        return users;
    }

//Einfuegung der Gruppen
   public List<Gruppen> parseCsvToGroups(String filePath) throws IOException {
        List<Gruppen> groups = new ArrayList<>();
        Charset encoding = StandardCharsets.UTF_8;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }


                String[] values = line.split(";");

                if (values.length < 6) {
                    System.err.println("Invalid data row: " + line);
                    continue;
                }

                String groupNumberString = values[3].trim();

                final String gruppe1 = GermanCharacterNormalizer.normalize(values[4].trim());
                String bereich = values[2].trim();


                Long groupNumber = parseLong(groupNumberString);

                String normalizedBereich = bereich.trim();
                Location location = locationRepository.findLocationByName(normalizedBereich).orElse(null);
                if
                (location == null) {
                    System.err.println("Location not found for name: " + bereich);
                    continue;
                }
                Gruppen group = new Gruppen();
                group.setId(groupNumber);
                group.setName(gruppe1);
                group.setLocation(location);

                groups.add(group);

            }
        }
        return groups;
    }
    public List<GruppenMitglieder> parseCsvGruppenMitglieders(String filePath) throws IOException {
        List<GruppenMitglieder> members = new ArrayList<>();
        Charset encoding = StandardCharsets.UTF_8;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }


                String[] values = line.split(";");

                if (values.length < 6) {
                    System.err.println("Invalid data row: " + line);
                    continue;
                }

                String groupNumberString = values[3].trim();
                String userName = values[1].trim();
                Long groupNumber = parseLong(groupNumberString);
                userName = userName.trim();
                User user = userRepository.findUserByUsername(userName).orElse(null);
                if
                (user == null) {
                    System.err.println("User not found for name: " + userName);
                    continue;
                }
                Gruppen gruppe = gruppenRepository.findById(groupNumber).orElse(null);
                if
                (gruppe == null) {
                    System.err.println("Group not found for group number: " + groupNumberString);
                    continue;
                }
                
            
            GruppenMitglieder groupMember = new GruppenMitglieder();

	        GroupMemberId memberId = new GroupMemberId(gruppe.getId(), user.getId());
	        groupMember.setId(memberId);
	        groupMember.setGruppe(gruppe);
	        groupMember.setUser(user);

	        members.add(groupMember);
            }
        }
        return members;
    }


}