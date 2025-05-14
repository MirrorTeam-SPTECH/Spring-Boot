package com.exemplo.prototipo_PI.prototipo_PI;

import com.exemplo.prototipo_PI.prototipo_PI.Service.CustomUserDetailsService;
import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryUsuarios;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private RepositoryUsuarios repositoryMock;  // Mock do repositório

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;  // A classe que será testada

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks
    }

    @Test
    void testLoadUserByUsernameComUsuarioExistente() {
        // Arrange
        String email = "teste@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameUsuarioNaoEncontrado() {
        // Arrange
        String email = "naoexiste@email.com";
        when(repositoryMock.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }

    @Test
    void testLoadUserByUsernameComSenhaVazia() {
        // Arrange
        String email = "teste@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("");  // Senha vazia

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailNulo() {
        // Arrange
        String email = null;
        when(repositoryMock.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }





    @Test
    void testLoadUserByUsernameComSenhaComEspacos() {
        // Arrange
        String email = "usuario@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("   senha123   ");  // Senha com espaços

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("   senha123   ", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComExcecaoDoRepositorio() {
        // Arrange
        String email = "erro@email.com";
        when(repositoryMock.findByEmail(email)).thenThrow(new RuntimeException("Erro no repositório"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }

    @Test
    void testLoadUserByUsernameComEmailComSubdominio() {
        // Arrange
        String email = "usuario@subdominio.dominio.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailComCaracteresEspeciais() {
        // Arrange
        String email = "usuario@dominio.com.br";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailComPontos() {
        // Arrange
        String email = "usuario.email@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailComAcentos() {
        // Arrange
        String email = "usuário@dominio.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComUsuarioSemRoles() {
        // Arrange
        String email = "usuario.sem.roles@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailMaiusculo() {
        // Arrange
        String email = "USUARIO@EMAIL.COM";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComSenhaNumerica() {
        // Arrange
        String email = "numerico@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("123456");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComSenhaLonga() {
        // Arrange
        String email = "usuario@longa.com";
        String senhaLonga = "senhaMuitoLongaComMuitosCaracteres1234567890";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha(senhaLonga);

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(senhaLonga, userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailCurto() {
        // Arrange
        String email = "a@b.c";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("abc123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("abc123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComSenhaComSimbolos() {
        // Arrange
        String email = "simbolos@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("@dm!n#123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("@dm!n#123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComSenhaComQuebraDeLinha() {
        // Arrange
        String email = "usuario@teste.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha\n123"); // Senha contendo quebra de linha

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha\n123", userDetails.getPassword());
    }


    @Test
    void testLoadUserByUsernameComEmailComTracos() {
        // Arrange
        String email = "usuario-teste@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailComUnderline() {
        // Arrange
        String email = "usuario_teste@email.com";
        Usuarios usuario = new Usuarios();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        when(repositoryMock.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameComEmailVazio() {
        // Arrange
        String email = "";
        when(repositoryMock.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }


}
