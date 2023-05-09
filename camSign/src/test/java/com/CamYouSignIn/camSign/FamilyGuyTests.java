package com.CamYouSignIn.camSign;

import com.CamYouSignIn.camSign.models.ApplicationUser;
import com.CamYouSignIn.camSign.repositories.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FamilyGuyTests {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private ApplicationUser peter;
    private ApplicationUser lois;
    private ApplicationUser stewie;

    @BeforeEach
    public void setUp() {
        peter = new ApplicationUser("petergriffin", "password123", "Peter", "Griffin", "1965-06-15", "I'm the man of the house.");
        lois = new ApplicationUser("loisgriffin", "password456", "Lois", "Griffin", "1969-10-11", "I'm the loving wife and mother.");
        stewie = new ApplicationUser("stewiegriffin", "password789", "Stewie", "Griffin", "1998-06-15", "I'm an evil baby genius.");
    }

    @Test
    public void testSaveFamilyGuyCharacters() {
        // Save the characters to the repository
        applicationUserRepository.save(peter);
        applicationUserRepository.save(lois);
        applicationUserRepository.save(stewie);

        // Retrieve the characters from the repository
        ApplicationUser retrievedPeter = applicationUserRepository.findByUsername("petergriffin");
        ApplicationUser retrievedLois = applicationUserRepository.findByUsername("loisgriffin");
        ApplicationUser retrievedStewie = applicationUserRepository.findByUsername("stewiegriffin");

        // Check if the characters were successfully saved and retrieved
        assertThat(retrievedPeter).isEqualTo(peter);
        assertThat(retrievedLois).isEqualTo(lois);
        assertThat(retrievedStewie).isEqualTo(stewie);
    }
}
