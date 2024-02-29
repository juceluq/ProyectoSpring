package proyecto_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto_spring.entities.Reserva;
import proyecto_spring.repositories.LibroRepository;
import proyecto_spring.repositories.ReservaRepository;
import proyecto_spring.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService{

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAll() {
        return  reservaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    @Transactional
    public Reserva save(Reserva reserva) {
        reserva.setUsuario(userRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        reserva.setLibro(libroRepository.findById(reserva.getLibro().getId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado")));
        return reservaRepository.save(reserva);
    }
    @Override
    @Transactional
    public Optional<Reserva> update(Long id, Reserva reserva) {
        return reservaRepository.findById(id).map(prestamoDb -> {
            prestamoDb.setFechaReserva(reserva.getFechaReserva());
            prestamoDb.setEstado(String.valueOf(reserva.getEstado()));
            prestamoDb.setUsuario(userRepository.findById(reserva.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
            prestamoDb.setLibro(libroRepository.findById(reserva.getLibro().getId())
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado")));
            reservaRepository.save(prestamoDb);
            return prestamoDb;
        });
    }

    @Override
    @Transactional
    public Optional<Reserva> delete(Long id) {
        Optional <Reserva> reservaOptional = reservaRepository.findById(id);
        reservaOptional.ifPresent(reservaDb -> reservaRepository.delete(reservaDb));
        return reservaOptional;
    }

    @Override
    public List<Reserva> findByEstado(Reserva.EstadoReserva estado) {
        return reservaRepository.findByEstado(estado);
    }

    @Override
    public List<Reserva> findByUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Reserva> findByFechaReservaBetween(LocalDate inicio, LocalDate fin) {
        return reservaRepository.findByFechaReservaBetween(inicio, fin);
    }

    @Override
    public List<Reserva> findByLibroId(Long libroId) {
        return reservaRepository.findByLibroId(libroId);
    }

    @Override
    public List<Reserva> buscarPorEstadoOrdenadoPorFecha(Reserva.EstadoReserva estado) {
        return reservaRepository.buscarPorEstadoOrdenadoPorFecha(estado);
    }
}
