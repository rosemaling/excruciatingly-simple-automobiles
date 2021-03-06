package com.galvanize.autos;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutosService {

    AutoRepository autoRepository;

    public AutosService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public AutosList getAutos() {
        return new AutosList(autoRepository.findAll());
    }


    public AutosList getAutos(String color, String make) {
        List<Automobile> autos = autoRepository.findByColorAndMake(color, make);
        if (!autos.isEmpty()) {
            return new AutosList(autos);
        }
        return null;
    }

    public AutosList getAutosByColor (String color) {
        List<Automobile> autos = autoRepository.findByColor(color);
        if (!autos.isEmpty()) {
            return new AutosList(autos);
        }
        return null;
    }

    public AutosList getAutosByMake (String make) {
        List<Automobile> autos = autoRepository.findByMake(make);
        if (!autos.isEmpty()) {
            return new AutosList(autos);
        }
        return null;
    }

    public Automobile addAuto(Automobile auto) {
        return autoRepository.save(auto);
    }
    
    public Automobile getAuto (String vin) {
        return autoRepository.findByVin(vin).orElse(null);
    }

    public Automobile updateAuto(String vin, String color, String owner) {
        Optional<Automobile> target = autoRepository.findByVin(vin);

        if (target.isPresent()) {
            target.get().setColor(color);
            target.get().setOwner(owner);
            return autoRepository.save(target.get());
        }
        return null;
    }

    public void deleteAuto(String vin) {
        Optional<Automobile> target = autoRepository.findByVin(vin);

        if (target.isPresent()) {
            autoRepository.delete(target.get());
        } else {
            throw new AutoNotFoundException();
        }
    }
}
