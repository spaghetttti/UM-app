package com.example.um.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    public List<Component> findAllComponents() {
        return componentRepository.findAll();
    }

    public Optional<Component> findComponentById(Long id) {
        return componentRepository.findById(id);
    }

    public Component saveComponent(Component component) {
        return componentRepository.save(component);
    }

    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }
}