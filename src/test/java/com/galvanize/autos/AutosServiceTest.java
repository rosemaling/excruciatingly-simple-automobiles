package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AutosServiceTest {

    private AutosService autoService;

    @Mock
    AutoRepository autoRepository;

    @BeforeEach
    void setUp() {
        autoService = new AutosService(autoRepository);
    }

    @Test
    void getAutos() {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autoRepository.findAll()).thenReturn(Arrays.asList(auto));
        AutosList autosList;
        autosList = autoService.getAutos();
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
    }

    @Test
    void testGetAutos() {
    }

    @Test
    void getAutosByColor() {
    }

    @Test
    void getAutosByMake() {
    }

    @Test
    void addAuto() {
    }

    @Test
    void getAuto() {
    }

    @Test
    void updateAuto() {
    }

    @Test
    void deleteAuto() {
    }
}