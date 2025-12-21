package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.PerfilResponse;
import br.com.fabioalvaro.bank123.bffbank.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff-bank123/usuario/v1/perfil")
@Tag(name = "perfil-controller", description = "Gerencia operações relacionadas ao perfil do usuário")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtém o perfil do usuário", description = "Retorna os detalhes do perfil do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, perfil retornado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado.")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('cliente_pf') and hasAuthority('read:perfil')")
    public ResponseEntity<PerfilResponse> obterPerfil(
            @Parameter(description = "E-mail do usuário no Firebase", required = true)
            @RequestHeader("x-email-firebase") String xEmailFirebase,
            @Parameter(description = "ID de correlação para rastreamento de requisições", example = "uuid-fake")
            @RequestHeader(value = "x-correlation-id", defaultValue = "uuid-fake") String xCorrelationId,
            @Parameter(description = "Token de autorização JWT", required = false)
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        
        PerfilResponse perfil = usuarioService.getPerfil(xEmailFirebase);
        return ResponseEntity.ok(perfil);
    }
}
