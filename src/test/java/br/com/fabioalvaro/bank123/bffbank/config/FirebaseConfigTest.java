package br.com.fabioalvaro.bank123.bffbank.config;

import com.google.firebase.FirebaseApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class FirebaseConfigTest {

    @AfterEach
    void tearDown() {
        // Limpa instâncias do Firebase após cada teste para evitar conflitos
        if (!FirebaseApp.getApps().isEmpty()) {
             FirebaseApp.getApps().forEach(FirebaseApp::delete);
        }
    }

    @Test
    void shouldLoadFirebaseAppFromEnvVarContainingJson() throws IOException {
        // Lê o conteúdo do arquivo real para simular a variável de ambiente
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/main/resources/serviceAccountKey.json")));

        FirebaseConfig config = new FirebaseConfig() {
            @Override
            protected String getFirebaseKeyJsonEnv() {
                return jsonContent;
            }
        };

        FirebaseApp app = config.firebaseApp();
        assertNotNull(app);
        assertEquals("[DEFAULT]", app.getName());
    }
    
    @Test
    void shouldLoadFirebaseAppFromEnvVarContainingBase64() throws IOException {
        // Lê o conteúdo, encoda em Base64 e simula
        byte[] jsonBytes = Files.readAllBytes(Paths.get("src/main/resources/serviceAccountKey.json"));
        String base64Content = Base64.getEncoder().encodeToString(jsonBytes);

        FirebaseConfig config = new FirebaseConfig() {
            @Override
            protected String getFirebaseKeyJsonEnv() {
                return base64Content;
            }
        };

        FirebaseApp app = config.firebaseApp();
        assertNotNull(app);
        assertEquals("[DEFAULT]", app.getName());
    }
}
