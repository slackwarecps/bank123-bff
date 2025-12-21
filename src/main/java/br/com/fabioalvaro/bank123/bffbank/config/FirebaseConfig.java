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

import com.google.firebase.auth.FirebaseAuth;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        String firebaseKeyJson = getFirebaseKeyJsonEnv();
        InputStream serviceAccount;

        if (firebaseKeyJson != null && !firebaseKeyJson.isBlank()) {
            System.out.println("Carregando credenciais do Firebase via variável de ambiente FIREBASE_KEY_JSON.");
            firebaseKeyJson = firebaseKeyJson.trim();
            System.out.println("Tamanho recebido: " + firebaseKeyJson.length());
            if (firebaseKeyJson.length() > 10) {
                System.out.println("Início da string: " + firebaseKeyJson.substring(0, 10));
            }
            
            // Remove aspas duplas no início e fim, se existirem
            if (firebaseKeyJson.startsWith("\"") && firebaseKeyJson.endsWith("\"")) {
                firebaseKeyJson = firebaseKeyJson.substring(1, firebaseKeyJson.length() - 1);
                System.out.println("Aspas removidas. Novo tamanho: " + firebaseKeyJson.length());
            }

            // Se não começar com '{', assumimos que é Base64
            if (!firebaseKeyJson.trim().startsWith("{")) {
                try {
                    System.out.println("Tentando decodificar Base64...");
                    // Usar MimeDecoder pois ele ignora quebras de linha e espaços
                    byte[] decoded = java.util.Base64.getMimeDecoder().decode(firebaseKeyJson);
                    String decodedString = new String(decoded, StandardCharsets.UTF_8);
                    
                    if (decodedString.trim().startsWith("{")) {
                        System.out.println("Credenciais decodificadas de Base64 com sucesso.");
                        firebaseKeyJson = decodedString;
                    } else {
                        System.out.println("Decodificação resultou em algo que não é JSON: " + 
                            (decodedString.length() > 10 ? decodedString.substring(0, 10) : decodedString));
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao tentar decodificar FIREBASE_KEY_JSON: " + e.getMessage());
                    e.printStackTrace();
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
