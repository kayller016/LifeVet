package com.vo;

import java.util.Random;

public class AppointmentInfo {
    private String appointmentId;
    private String memberName;
    private String vetName;
    private String scheduleDate;
    private String scheduleTime;
    private String status;
    private String petName;

    public AppointmentInfo(){}

    public AppointmentInfo(String petName, String ownerName, String vetName, String appointmentDate, String appointmentTime){
        this.memberName = ownerName;
        this.petName = petName;
        this.vetName = vetName;
        this.scheduleDate = appointmentDate;
        this.scheduleTime = appointmentTime;
        this.status = "Reserved";

        Random rand = new Random();
        int n = rand.nextInt(1000);

        this.appointmentId = String.valueOf(n);

    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petId) {
        this.petName = petId;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
