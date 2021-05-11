package com.galvanize.autos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

    ObjectMapper mapper = new ObjectMapper();

    //- GET: /api/autos Returns list of all autos in db when autos exist
    @Test
    void getAutos_returnsListOfAutos_whenExists () throws Exception {
        List<Automobile> automobiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1990+i, "Ford", "Mustang", "7F03Z0102"+i));
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
            automobiles.add(new Automobile(1990+i, "Ford", "Mustang", "7F03Z0102"+i));
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
            automobiles.add(new Automobile(1990+i, "Ford", "Mustang", "7F03Z0102"+i));
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
            automobiles.add(new Automobile(1990+i, "Ford", "Mustang", "7F03Z0102"+i));
        }

        when(autosService.getAutosByMake(anyString())).thenReturn(new AutosList(automobiles));

        mockMvc.perform(get("/api/autos?make=Ford"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    //- POST: /api/autos Returns the created entry
    @Test
    void postAutos_returnCreatedAuto() throws Exception {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autosService.addAuto(any(Automobile.class))).thenReturn(auto);
        mockMvc.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(auto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vin").value("7F03Z0102"));
    }
     //- POST: /api/autos Returns 400 for bad request. e.g. insufficient request body info.
     @Test
     void postAutos_throwErrorForBadRequest() throws Exception {

         when(autosService.addAuto(any(Automobile.class))).thenThrow(InvalidAutoException.class);
         mockMvc.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                 .content("{\"year\":1990,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":null,\"owner\":null,\"vin\":\"7F03Z01025\"}\""))
                 .andExpect(status().isBadRequest());
     }

    //- GET: /api/autos/{vin} Returns the requested auto with vin number if it exist in db
    @Test
    void getAuto_withVinNumber_returnsAutomobile () throws Exception {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autosService.getAuto(anyString())).thenReturn(auto);

        mockMvc.perform(get("/api/autos/" + auto.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("make").value("Ford"));
    }
    //- GET: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db
    @Test
    void getAuto_withVinNumber_noContent_whenVinNotExists () throws Exception {
        when(autosService.getAuto(anyString())).thenReturn(null);

        mockMvc.perform(get("/api/autos/ghfvhgfg5456"))
                .andExpect(status().isNoContent());
    }

    //- PATCH: /api/autos/{vin} Returns the requested auto after PATCH if it exists in db
    @Test
    void updateAuto_withVinNumberAndColor_whenExists () throws Exception {
        Automobile auto = new Automobile(1990, "Ford", "Mustang", "7F03Z0102");
        when(autosService.updateAuto(anyString(), anyString(), anyString())).thenReturn(auto);

        mockMvc.perform(patch("/api/autos/" + auto.getVin())
        .contentType(MediaType.APPLICATION_JSON).content("{\"color\":\"pink\",\"owner\":\"Rob\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("color").value("pink"))
                .andExpect(jsonPath("owner").value("Rob"));
    }
    //- PATCH: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db
    @Test
    void updateAuto_withVinNumberAndColor_badRequest_whenNotExists () throws Exception {
        doThrow(new AutoNotFoundException()).when(autosService).updateAuto(anyString(), anyString(), anyString());
        mockMvc.perform(patch("/api/autos/AABBCC")
                .contentType(MediaType.APPLICATION_JSON).content("{\"color\":\"pink\",\"owner\":\"Rob\"}"))
                .andExpect(status().isNoContent());
    }
    //- PATCH: /api/autos{vin} Returns 400 for bad request
    @Test
    void updateAuto_throwErrorForBadRequest() throws Exception {

        when(autosService.updateAuto(anyString(), anyString(), anyString())).thenThrow(InvalidAutoException.class);
        mockMvc.perform(patch("/api/autos/AABBCC").contentType(MediaType.APPLICATION_JSON)
                .content("{\"year\":1990,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":\"pink\",\"owner\":\"Rob\",\"vin\":\"7F03Z01025\"}\""))
                .andExpect(status().isBadRequest());
    }

    //- DELETE: /api/autos/{vin} Returns 202 for successful deletion
    @Test
    void deleteAuto_withVin_returnAccepted () throws Exception {
        mockMvc.perform(delete("/api/autos/AABBCC"))
                .andExpect(status().isAccepted());
        verify(autosService).deleteAuto(anyString());
    }
    //- DELETE: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db

    @Test
    void deleteAuto_withVin_returnNoContent_whenNotExists () throws Exception {
        doThrow(new AutoNotFoundException()).when(autosService).deleteAuto(anyString());
        mockMvc.perform(delete("/api/autos/AABBCC"))
                .andExpect(status().isNoContent());
    }

}
