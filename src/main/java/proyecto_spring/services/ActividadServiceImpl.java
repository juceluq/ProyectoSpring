package proyecto_spring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto_spring.entities.Actividad;
import proyecto_spring.repositories.ActividadRepository;
import proyecto_spring.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadServiceImpl implements ActividadService{

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> findAll() {
        return  actividadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Actividad> findById(Long id) {
        return actividadRepository.findById(id);
    }

    @Override
    @Transactional
    public Actividad save(Actividad actividad) {
        actividad.setUsuario(userRepository.findById(actividad.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return actividadRepository.save(actividad);
    }
    @Override
    @Transactional
    public Optional<Actividad> update(Long id, Actividad actividad) {
        return actividadRepository.findById(id).map(actividadDb -> {
            actividadDb.setFechaHoraActividad(actividad.getFechaHoraActividad());
            actividadDb.setUsuario(userRepository.findById(actividad.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
            actividadRepository.save(actividadDb);
            return actividadDb;
        });
    }

    @Override
    @Transactional
    public Optional<Actividad> delete(Long id) {
        Optional <Actividad> actividadOptional = actividadRepository.findById(id);
        actividadOptional.ifPresent(actividadDb -> actividadRepository.delete(actividadDb));
        return actividadOptional;
    }


}
