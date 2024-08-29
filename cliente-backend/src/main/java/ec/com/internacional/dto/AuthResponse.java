package ec.com.internacional.dto;

public class AuthResponse {
 private String token;
 private String sessionToken;
 private String message;

 // Constructor
 public AuthResponse(String token, String sessionToken, String message) {
     this.token = token;
     this.sessionToken = sessionToken;
     this.message = message;
 }

 // Getters y Setters
 public String getToken() {
     return token;
 }

 public void setToken(String token) {
     this.token = token;
 }

 public String getSessionToken() {
     return sessionToken;
 }

 public void setSessionToken(String sessionToken) {
     this.sessionToken = sessionToken;
 }

 public String getMessage() {
     return message;
 }

 public void setMessage(String message) {
     this.message = message;
 }
}
