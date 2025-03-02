package com.essensbestellung.Digitale_Essensbestellung;
import com.essensbestellung.service.impl.IAuthServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.essensbestellung.dto.DtoUserIU;
import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.Location;
import com.essensbestellung.entities.User;
import com.essensbestellung.enums.Role;
import com.essensbestellung.jwt.AuthRequest;
import com.essensbestellung.jwt.AuthResponse;
import com.essensbestellung.jwt.JwtService;
import com.essensbestellung.repository.IUserRepository;

public class IAuthServiceImplTest {

    @InjectMocks
    private IAuthServiceImpl authService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private AuthRequest authRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // AuthRequest initialisieren
        authRequest = new AuthRequest();
        authRequest.setUsername("testUser");
        authRequest.setPassword("testPassword");
        authRequest.setRole("ADMIN");
        authRequest.setFullname("Test User");

        // User-Objekt initialisieren
        mockUser = new User();
       // mockUser.setId(1L);
        mockUser.setUsername("testUser");
        mockUser.setPassword("encodedPassword");
        mockUser.setRole(Role.ADMIN);
        mockUser.setFullname("Test User");
    }

    @Test
    void testAuthenticate_Success() {
        // Mock-Verhalten definieren
        when(userRepository.findByUsername(authRequest.getUsername()))
            .thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(mockUser)).thenReturn("mockToken");
        when(authenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(null); // Hier sicherstellen, dass keine Exception geworfen wird

        // Test-Methode ausführen
        AuthResponse response = authService.authenticate(authRequest);

        // Ergebnisse validieren
        assertNotNull(response, "Die Authentifizierung sollte erfolgreich sein.");
        assertEquals("mockToken", response.getToken(), "Das Token sollte korrekt generiert werden.");
        assertEquals(mockUser.getId(), response.getId(), "Die Benutzer-ID sollte übereinstimmen.");
    }

    @Test
    void testAuthenticate_Failure() {
        // Benutzer nicht vorhanden
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.empty());

        // Test-Methode ausführen
        AuthResponse response = authService.authenticate(authRequest);

        // Validierung: Authentifizierung schlägt fehl
        assertNull(response, "Die Authentifizierung sollte fehlschlagen, wenn der Benutzer nicht existiert.");
    }

    @Test
    void testRegister_Success() {
        // Arrange
        Location location = new Location(); // Mock für Standort
        location.setId(1L);
        authRequest.setLocation(location); // Optional je nach Rolle

        String rawPassword = authRequest.getPassword();
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        // Act
        DtoUserIU result = authService.register(authRequest);

        // Assert
        assertNotNull(result, "Der registrierte Benutzer sollte nicht null sein.");
        assertEquals(authRequest.getUsername(), result.getUsername(), "Der Benutzername sollte korrekt sein.");
        assertEquals(Role.ADMIN, result.getRole(), "Die Rolle sollte korrekt sein.");
    }
}
