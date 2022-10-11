package me.juan.bot.api;
import lombok.Getter;

@Getter
public class ServiceResponse<T> {

    private T value;
    private String errorMessage;

    public ServiceResponse(T response) {
        this.value = response;
    }

    public ServiceResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return errorMessage == null && value != null;
    }

}
