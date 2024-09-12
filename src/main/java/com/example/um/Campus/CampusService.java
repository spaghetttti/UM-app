package com.example.um.Campus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampusService {

    @Autowired
    private CampusRepository campusRepository;

    public List<Campus> findAllCampuses() {
        return campusRepository.findAll();
    }

    public Optional<Campus> findCampusById(Long id) {
        return campusRepository.findById(id);
    }

    public Campus saveCampus(Campus campus) {
        return campusRepository.save(campus);
    }

    public void deleteCampus(Long id) {
        campusRepository.deleteById(id);
    }
}
