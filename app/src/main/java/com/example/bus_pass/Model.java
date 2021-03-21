package com.example.bus_pass;

public class Model {

    String busno,busnumber,drivername,drivernumber,route;
    Model(){}
    public Model(String busno, String busnumber, String drivername, String drivernumber, String route) {
        this.busno = busno;
        this.busnumber = busnumber;
        this.drivername = drivername;
        this.drivernumber = drivernumber;
        this.route = route;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getBusnumber() {
        return busnumber;
    }

    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getDrivernumber() {
        return drivernumber;
    }

    public void setDrivernumber(String drivernumber) {
        this.drivernumber = drivernumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
