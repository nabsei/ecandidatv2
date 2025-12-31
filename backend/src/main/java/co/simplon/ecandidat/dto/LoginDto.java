package co.simplon.ecandidat.dto;

public class LoginDto {

    private String token;
    private String email;

    public LoginDto() {
    }

    public LoginDto(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
