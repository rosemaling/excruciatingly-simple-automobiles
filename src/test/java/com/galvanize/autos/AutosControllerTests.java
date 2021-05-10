package com.galvanize.autos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutosController.class)
public class AutosControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutosService autosService;

    //- GET: /api/autos Returns list of all autos in db when autos exist
    @Test
    void getAutos_returnsListOfAutos_whenExists () throws Exception {
        List<Automobile> automobiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile("1990"+i, "Ford", "Mustang", "7F03Z0102"+i));
        }

        when(autosService.getAutos()).thenReturn(new AutosList(automobiles));

        mockMvc.perform(get("/api/autos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    //- GET: /api/autos Returns 204 when no autos found when no autos in db
    @Test
    void getAutos_returnNoContent_whenNotExists() throws Exception {
        when(autosService.getAutos()).thenReturn(new AutosList());
        mockMvc.perform(get("/api/autos"))
                .andExpect(status().isNoContent());
    }

    //- GET: /api/autos?make=Volkswagen&color=blue Returns list of all blue Volkswagens in db
    @Test
    void getAutos_withParams_returnsListOfAutos() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile("1990"+i, "Ford", "Mustang", "7F03Z0102"+i));
        }

        when(autosService.getAutos(anyString(), anyString())).thenReturn(new AutosList(automobiles));

        mockMvc.perform(get("/api/autos?make=Ford&color=blue"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    //- GET: /api/autos?color=blue Returns list of all blue cars in db
    @Test
    void getAutos_withColorParam_returnsListOfAutos() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile("1990"+i, "Ford", "Mustang", "7F03Z0102"+i));
        }

        when(autosService.getAutosByColor(anyString())).thenReturn(new AutosList(automobiles));

        mockMvc.perform(get("/api/autos?color=blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    //- GET: /api/autos?make=Volkswagen Returns list of all Volkswagens in db
    @Test
    void getAutos_withMakeParam_returnsListOfAutos() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile("1990"+i, "Ford", "Mustang", "7F03Z0102"+i));
        }

        when(autosService.getAutosByMake(anyString())).thenReturn(new AutosList(automobiles));

        mockMvc.perform(get("/api/autos?make=Ford"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    //- POST: /api/autos Returns the created entry
    //- POST: /api/autos Returns 400 for bad request. e.g. insufficient request body info.

    //- GET: /api/autos/{vin} Returns the requested auto with vin number if it exist in db
    //- GET: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db

    //- PATCH: /api/autos/{vin} Returns the requested auto after PATCH if it exists in db
    //- PATCH: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db
    //- PATCH: /api/autos{vin} Returns 400 for bad request

    //- DELETE: /api/autos/{vin} Returns 202 for successful deletion
    //- DELETE: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db

}
