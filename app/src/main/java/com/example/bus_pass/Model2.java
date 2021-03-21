package com.example.bus_pass;

public class Model2 {
    String amount,passId,rollno,status,type,valid;
    Model2(){

    }
    public Model2(String amount, String passId, String rollno, String status, String type, String valid) {
        this.amount = amount;
        this.passId = passId;
        this.rollno = rollno;
        this.status = status;
        this.type = type;
        this.valid = valid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
