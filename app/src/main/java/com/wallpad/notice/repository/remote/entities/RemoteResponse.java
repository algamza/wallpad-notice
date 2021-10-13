package com.wallpad.notice.repository.remote.entities;

public class RemoteResponse {
    private String Error_Cd;
    private String Error_Nm;

    public RemoteResponse(String error_Cd, String error_Nm) {
        Error_Cd = error_Cd;
        Error_Nm = error_Nm;
    }

    public String getError_Cd() {
        return Error_Cd;
    }

    public void setError_Cd(String error_Cd) {
        Error_Cd = error_Cd;
    }

    public String getError_Nm() {
        return Error_Nm;
    }

    public void setError_Nm(String error_Nm) {
        Error_Nm = error_Nm;
    }
}
