package com.pilvadim.teplota.model.response;

public class WeatherResponse {

    private InnerResponseContent main;

    private Long dt;

    public InnerResponseContent getMain() {
        return main;
    }

    public void setMain( InnerResponseContent main ) {
        this.main = main;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }
}
