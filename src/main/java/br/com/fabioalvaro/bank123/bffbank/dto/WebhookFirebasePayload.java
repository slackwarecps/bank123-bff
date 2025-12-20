package br.com.fabioalvaro.bank123.bffbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebhookFirebasePayload {
    private String uid;
    private String email;
    private String displayName;
    private String createdAt;
    private String provider;
}