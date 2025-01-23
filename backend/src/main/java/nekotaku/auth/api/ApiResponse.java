package nekotaku.auth.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiResponse {
    private int httpCode;
    private HttpStatus status;
    private Object message;

    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.httpCode = this.status.value();
    }
}
