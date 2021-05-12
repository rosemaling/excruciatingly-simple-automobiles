package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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
    void getAutos_noParams_returnAutosList() {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autoRepository.findAll()).thenReturn(Arrays.asList(auto));
        AutosList autosList;
        autosList = autoService.getAutos();
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
    }

    @Test
    void testGetAutos_withColorAndMake_returnAutosList() {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        auto.setColor("Pink");
        when(autoRepository.findByColorAndMake(anyString(), anyString())).thenReturn(Arrays.asList(auto));
        AutosList autosList = autoService.getAutos("Pink", "Ford");
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
    }

    @Test
    void getAutosByColor_withColor_returnAutosList() {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        auto.setColor("Pink");
        when(autoRepository.findByColor(anyString())).thenReturn(Arrays.asList(auto));
        AutosList autosList = autoService.getAutosByColor("Pink");
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
    }

    @Test
    void getAutosByMake_withMake_returnAutosList() {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autoRepository.findByMake(anyString())).thenReturn(Arrays.asList(auto));
        AutosList autosList = autoService.getAutosByMake("Ford");
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
    }

    @Test
    void addAuto_returnNewAuto() {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autoRepository.save(any(Automobile.class))).thenReturn(auto);
        Automobile automobile = autoService.addAuto(auto);
        assertThat(automobile).isNotNull();
        assertThat(automobile.getVin()).isEqualTo("7F03Z0102");
    }

    @Test
    void getAuto_withVin_returnAuto() {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autoRepository.findByVin(anyString())).thenReturn(java.util.Optional.of(auto));

        Automobile automobile = autoService.getAuto(auto.getVin());
        assertThat(automobile).isNotNull();
        assertThat(automobile.getVin()).isEqualTo("7F03Z0102");
    }

    @Test
    void updateAuto() {
    }

    @Test
    void deleteAuto() {
    }
}