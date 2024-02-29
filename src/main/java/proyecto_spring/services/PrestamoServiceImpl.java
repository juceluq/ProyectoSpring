package proyecto_spring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto_spring.entities.Libro;
import proyecto_spring.entities.Prestamo;
import proyecto_spring.entities.User;
import proyecto_spring.repositories.LibroRepository;
import proyecto_spring.repositories.PrestamoRepository;
import proyecto_spring.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements PrestamoService{

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibroRepository libroRepository;

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
        prestamo.setUsuario(userRepository.findById(prestamo.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        prestamo.setLibro(libroRepository.findById(prestamo.getLibro().getId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado")));
        return prestamoRepository.save(prestamo);
    }
    @Override
    @Transactional
    public Optional<Prestamo> update(Long id, Prestamo prestamo) {
        return prestamoRepository.findById(id).map(prestamoDb -> {
            prestamoDb.setFechaLimite(prestamo.getFechaLimite());
            prestamoDb.setDevuelto(prestamo.isDevuelto());
            prestamoDb.setUsuario(userRepository.findById(prestamo.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
            prestamoDb.setLibro(libroRepository.findById(prestamo.getLibro().getId())
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado")));
            prestamoRepository.save(prestamoDb);
            return prestamoDb;
        });
    }

    @Override
    @Transactional
    public Optional<Prestamo> delete(Long id) {
        Optional <Prestamo> prestamoOptional = prestamoRepository.findById(id);
        prestamoOptional.ifPresent(prestamoDb -> prestamoRepository.delete(prestamoDb));
        return prestamoOptional;
    }

    @Override
    public List<Prestamo> findByLibroId(Long libroId) {
        return prestamoRepository.findByLibroId(libroId);
    }

    @Override
    public List<Prestamo> findByUsuarioId(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Prestamo> findPrestamosAtrasados() {
        LocalDate fechaActual = LocalDate.now();
        return prestamoRepository.findPrestamosAtrasados(fechaActual);
    }

}
