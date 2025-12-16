package br.com.fabioalvaro.bank123.bffbank.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        String firebaseKeyJson = getFirebaseKeyJsonEnv();
        InputStream serviceAccount;

        if (firebaseKeyJson != null && !firebaseKeyJson.isBlank()) {
            System.out.println("Carregando credenciais do Firebase via variável de ambiente FIREBASE_KEY_JSON.");
            firebaseKeyJson = firebaseKeyJson.trim();
            
            // Tenta decodar Base64 se não começar com '{'
            if (!firebaseKeyJson.startsWith("{")) {
                try {
                    byte[] decoded = java.util.Base64.getDecoder().decode(firebaseKeyJson);
                    String decodedString = new String(decoded, StandardCharsets.UTF_8);
                    if (decodedString.trim().startsWith("{")) {
                        System.out.println("Credenciais decodificadas de Base64 com sucesso.");
                        firebaseKeyJson = decodedString;
                    }
                } catch (IllegalArgumentException e) {
                    // Não é Base64 válido, prossegue tentando usar como string original
                    System.out.println("Aviso: Valor de FIREBASE_KEY_JSON não parece ser JSON nem Base64 válido. Tentando processar como está.");
                }
            }

            serviceAccount = new ByteArrayInputStream(firebaseKeyJson.getBytes(StandardCharsets.UTF_8));
        } else {
            System.out.println("Variável FIREBASE_KEY_JSON não definida. Tentando carregar serviceAccountKey.json do classpath.");
            // Tenta carregar do classpath (dentro do jar/resources) como fallback
            serviceAccount = getClass()
                    .getClassLoader()
                    .getResourceAsStream("serviceAccountKey.json");
        }

        if (serviceAccount == null) {
            throw new IOException("Configuração do Firebase não encontrada. " +
                    "Defina a variável de ambiente FIREBASE_KEY_JSON ou garanta que o arquivo serviceAccountKey.json esteja no classpath.");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // Evita erro de inicializar duas vezes se o Spring reiniciar
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }

    protected String getFirebaseKeyJsonEnv() {
        return System.getenv("FIREBASE_KEY_JSON");
    }
}
