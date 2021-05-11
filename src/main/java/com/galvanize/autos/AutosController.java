package com.galvanize.autos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AutosController {
    private AutosService autosService;

    public AutosController(AutosService autosService) {
        this.autosService = autosService;
    }

    @GetMapping("/api/autos")
    public ResponseEntity<AutosList> getAutos(@RequestParam(required = false) String make, @RequestParam(required = false) String color) {
        AutosList list;
        if (make == null && color == null) {
            list = autosService.getAutos();
        } else if (make == null){
            list = autosService.getAutosByColor(color);
        } else if (color == null){
            list = autosService.getAutosByMake(make);
        } else {
            list = autosService.getAutos(color, make);
        }

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @PostMapping("/api/autos")
    public Automobile addAuto(@RequestBody Automobile auto) {
        return autosService.addAuto(auto);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidAutoExceptionHandler(InvalidAutoException e) {

    }
    
    @GetMapping("/api/autos/{vin}")
    public ResponseEntity<Automobile> getAuto(@PathVariable String vin) {
        Automobile target = autosService.getAuto(vin);
                if (target == null) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.ok(target);
                }
    }
}
