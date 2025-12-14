package daw2.dwes.simulacro.service;

import daw2.dwes.simulacro.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import daw2.dwes.simulacro.model.Module;

import java.util.List;

@Service
public class ModuleService {

    private ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> findAll() {
        return moduleRepository.findAll();
    }

    public List<Module> findAllById(List<Long> id) {
        return moduleRepository.findAllById(id);

    }

    public Module findById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));
    }

    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }
}