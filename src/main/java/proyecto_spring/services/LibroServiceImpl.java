package proyecto_spring.services;

import proyecto_spring.entities.Libro;
import proyecto_spring.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return  libroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    @Override
    @Transactional
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    @Transactional
    public Optional <Libro> update(Long id, Libro libro) {
        Optional <Libro> productOptional = libroRepository.findById(id);
        if(productOptional.isPresent()){
            Libro libroDb = productOptional.orElseThrow();
           libroDb.setAutor(libro.getAutor());
           libroDb.setIsbn(libro.getIsbn());
            libroDb.setDisponibilidad(libro.isDisponibilidad());
            return Optional.of(libroRepository.save(libroDb));
        }
        return productOptional;
    }

    @Override
    @Transactional
    public Optional<Libro> delete(Long id) {
        Optional <Libro> productOptional = libroRepository.findById(id);
        productOptional.ifPresent(libroDb -> libroRepository.delete(libroDb));
    return productOptional;
    }
}
