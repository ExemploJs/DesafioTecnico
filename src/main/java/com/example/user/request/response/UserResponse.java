package com.example.user.request.response;

import java.io.Serializable;
import java.util.Objects;

public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userName;

    public UserResponse() {
    }

    public UserResponse(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
