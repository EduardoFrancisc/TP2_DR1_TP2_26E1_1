package infnet.edu.br.tp2;

import infnet.edu.br.tp2.model.*;
import infnet.edu.br.tp2.repository.UsuarioRepository;
import infnet.edu.br.tp2.repository.RoleRepository;
import infnet.edu.br.tp2.repository.OrganizacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Testes de Validação do Mapeamento Legacy")
public class MapeamentoTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Test
    @DisplayName("Deve carregar usuários e suas respectivas roles")
    void deveCarregarUsuariosERoles() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        assertThat(usuarios).isNotEmpty();

        Usuario usuario = usuarios.get(0);
        assertThat(usuario.getRoles()).isNotNull();
        System.out.println("Usuário: " + usuario.getNome() + " | Roles: " + usuario.getRoles().size());
    }

    @Test
    @DisplayName("Deve carregar roles e suas permissões")
    void deveCarregarRolesEPermissoes() {
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).isNotEmpty();

        Role role = roles.get(0);
        assertThat(role.getPermissions()).isNotNull();
        System.out.println("Role: " + role.getNome() + " | Permissões: " + role.getPermissions().size());
    }

    @Test
    @DisplayName("Deve persistir um novo usuário em uma organização existente")
    void devePersistirUsuarioEmOrganizacaoExistente() {
        Organizacao org = organizacaoRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Banco vazio! Rode o Docker primeiro."));

        Usuario novo = new Usuario();
        novo.setNome("Recruta de Teste");
        novo.setEmail("teste.final@infnet.edu.br");
        novo.setSenhaHash("hash_segura_123");
        novo.setStatus("ATIVO");

        OffsetDateTime agora = OffsetDateTime.now();
        novo.setCreatedAt(agora);
        novo.setUpdatedAt(agora);
        novo.setOrganizacao(org);

        Usuario salvo = usuarioRepository.save(novo);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getStatus()).isEqualTo("ATIVO");

    }
}
