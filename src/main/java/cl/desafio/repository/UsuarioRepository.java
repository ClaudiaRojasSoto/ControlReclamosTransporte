package cl.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.desafio.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
