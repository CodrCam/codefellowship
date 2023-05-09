package com.CamYouSignIn.camSign;

import com.CamYouSignIn.camSign.models.ApplicationUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApplicationUserTests {

    private ApplicationUser user;

    @BeforeEach
    public void setUp() {
        user = new ApplicationUser("peter_griffin", "Lois123", "Peter", "Griffin", "1970-05-15", "I love Pawtucket Patriot Ale and shenanigans!");
    }

    @Test
    public void testApplicationUserCreation() {
        assertNotNull(user, "User should not be null");
    }

    @Test
    public void testGetUsername() {
        assertEquals("peter_griffin", user.getUsername(), "Username should match the one provided");
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Peter", user.getFirstName(), "First name should match the one provided");
    }

    @Test
    public void testGetLastName() {
        assertEquals("Griffin", user.getLastName(), "Last name should match the one provided");
    }

    @Test
    public void testGetDateOfBirth() {
        assertEquals(LocalDate.parse("1970-05-15"), user.getDateOfBirth(), "Date of birth should match the one provided");
    }

    @Test
    public void testGetBio() {
        assertEquals("I love Pawtucket Patriot Ale and shenanigans!", user.getBio(), "Bio should match the one provided");
    }
}
