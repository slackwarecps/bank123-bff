package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.PerfilResponse;
import br.com.fabioalvaro.bank123.bffbank.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff-bank123/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<PerfilResponse> obterPerfil(
            @RequestHeader("user-id") String userId) {
        
        PerfilResponse perfil = usuarioService.getPerfil(userId);
        return ResponseEntity.ok(perfil);
    }
}
