package proyecto_spring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto_spring.entities.Prestamo;
import proyecto_spring.repositories.PrestamoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements PrestamoService{

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> findAll() {
        return  prestamoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prestamo> findById(Long id) {
        return prestamoRepository.findById(id);
    }

    @Override
    @Transactional
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    @Transactional
    public Optional <Prestamo> update(Long id, Prestamo prestamo) {
        Optional <Prestamo> productOptional = prestamoRepository.findById(id);
        if(productOptional.isPresent()){
            Prestamo prestamoDb = productOptional.orElseThrow();
            prestamoDb.setFechaLimite(prestamo.getFechaLimite());
            prestamoDb.setLibroId(prestamo.getLibroId());
            prestamoDb.setUsuarioId(prestamo.getUsuarioId());
            return Optional.of(prestamoRepository.save(prestamoDb));
        }
        return productOptional;
    }

    @Override
    @Transactional
    public Optional<Prestamo> delete(Long id) {
        Optional <Prestamo> prestamoOptional = prestamoRepository.findById(id);
        prestamoOptional.ifPresent(prestamoDb -> prestamoRepository.delete(prestamoDb));
        return prestamoOptional;
    }
}
