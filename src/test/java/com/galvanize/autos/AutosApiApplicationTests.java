package com.galvanize.autos;

import org.apache.coyote.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations= "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutosApiApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AutoRepository autoRepository;

    List<Automobile> testAutos;
    Random r = new Random();


    @BeforeEach
    void setUp() {
        this.testAutos = new ArrayList<>();
        String[] colors = {"calypso blue", "strawberry milkshake", "forest green", "pearl white", "beep pink", "mint creme", "dark olive grey", "snow", "light pink", "russian red", "ghost white", "sorbet" };
        for (int i = 0; i < 72; i++) {
            Automobile auto;
            if (i % 12 == 0) {
                auto = new Automobile(1990, "Volkswagen", "Beetle", "HIHIHI"+(i * 13));
                auto.setColor(colors[r.nextInt(12)]);
            } else if (i % 3 == 0) {
                auto = new Automobile(2008, "Toyota", "Tundra", "YOYOYO"+(i * 16));
                auto.setColor(colors[r.nextInt(12)]);
            } else {
                auto = new Automobile(1950, "Lincoln", "Cosmopolitan", "HEYHEY"+(i * 17));
                auto.setColor(colors[r.nextInt(12)]);
            }
            testAutos.add(auto);
        }
        autoRepository.saveAll(testAutos);
    }

    @AfterEach
    void tearDown() {
        autoRepository.deleteAll();
    }


	@Test
	void contextLoads() {
	}

	@Test
    void getAutos_returnAutosList(){
        ResponseEntity<AutosList> response = restTemplate.getForEntity("/api/autos", AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isEmpty()).isFalse();
    }

    @Test
    void getAutos_withParams_returnAutosList(){
        int index = r.nextInt(72);
        String color = testAutos.get(index).getColor();
        String make = testAutos.get(index).getMake();
        ResponseEntity<AutosList> response = restTemplate.getForEntity(String.format("/api/autos?color=%s&make=%s", color, make), AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEmpty()).isFalse();
        assertThat(response.getBody().getAutomobiles().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void addAuto_returnCreatedAuto(){
        Automobile auto = new Automobile(1990, "Volkswagen", "Beetle", "IAMUNIQUE");
        String vin = auto.getVin();


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Automobile> request = new HttpEntity<>(auto, headers);

        ResponseEntity<Automobile> response = restTemplate.postForEntity("/api/autos", request, Automobile.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getVin()).isEqualTo(vin);
    }

}
