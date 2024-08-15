package cl.desafio.service;

import cl.desafio.dto.UsuarioDTO;
import cl.desafio.entity.Rol;
import cl.desafio.entity.Usuario;
import cl.desafio.repository.RolRepository;
import cl.desafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioDTO registerUser(UsuarioDTO usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setTelefono(usuarioDto.getTelefono());

        Set<Rol> roles = new HashSet<>();
        Rol userRole = rolRepository.findByNombre("ROLE_USER");
        if (userRole != null) {
            roles.add(userRole);
        }
        usuario.setRoles(roles);

        Usuario savedUser = usuarioRepository.save(usuario);
        return convertToDto(savedUser);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    private UsuarioDTO convertToDto(Usuario usuario) {
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setApellido(usuario.getApellido());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setUsername(usuario.getUsername());
        usuarioDto.setTelefono(usuario.getTelefono());
        Set<String> roles = new HashSet<>();
        usuario.getRoles().forEach(rol -> roles.add(rol.getNombre()));
        usuarioDto.setRoles(roles);
        return usuarioDto;
    }
}
