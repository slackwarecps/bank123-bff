import br.com.fabioalvaro.bank123.bffbank.dto.PerfilResponse;
import br.com.fabioalvaro.bank123.bffbank.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff-bank123/v1/perfil") // Changed the request mapping to match the OpenAPI spec
@Tag(name = "perfil-controller", description = "Gerencia operações relacionadas ao perfil do usuário") // Added Tag for OpenAPI
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
    public ResponseEntity<PerfilResponse> obterPerfil(
            @Parameter(description = "ID do usuário (Firebase UID)", required = true)
            @RequestHeader("user-id") String userId,
            @Parameter(description = "ID da conta do usuário", required = true)
            @RequestHeader("x-account-id") Integer xAccountId,
            @Parameter(description = "ID de correlação para rastreamento de requisições", example = "uuid-fake")
            @RequestHeader(value = "x-correlationId", defaultValue = "uuid-fake") String xCorrelationId,
            @Parameter(description = "Token de autorização JWT", required = false)
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        // Log the received headers for debugging purposes (optional)
        System.out.println("Received headers: user-id=" + userId + ", x-account-id=" + xAccountId +
                ", x-correlationId=" + xCorrelationId + ", Authorization=" + authorization);
        
        // Pass relevant headers to the service layer if needed
        PerfilResponse perfil = usuarioService.getPerfil(userId, xAccountId, xCorrelationId, authorization);
        return ResponseEntity.ok(perfil);
    }
}
