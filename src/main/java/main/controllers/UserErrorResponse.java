package main.controllers;

public class UserErrorResponse {
    private int code;
    private String message;

//    public UserErrorResponse(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
