package com.my.day19.demo05SpringJDBC;

/*
    练习，封装数据
 */
public class Meeting {
    private String meeting_id;
    private String meeting_name;
    private String applicant_id;
    private String applicant_name;
    private Integer applicant_date;
    private String start_time;
    private String end_time;
    private String display_date;
    private String display_time;
    private String apply_time;


    public Meeting() {
    }

    public Meeting(String meeting_id, String meeting_name, String applicant_id, String applicant_name, int applicant_date, String start_time, String end_time, String display_date, String display_time, String apply_time) {
        this.meeting_id = meeting_id;
        this.meeting_name = meeting_name;
        this.applicant_id = applicant_id;
        this.applicant_name = applicant_name;
        this.applicant_date = applicant_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.display_date = display_date;
        this.display_time = display_time;
        this.apply_time = apply_time;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meeting_id='" + meeting_id + '\'' +
                ", meeting_name='" + meeting_name + '\'' +
                ", applicant_id='" + applicant_id + '\'' +
                ", applicant_name='" + applicant_name + '\'' +
                ", applicant_date=" + applicant_date +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", display_date='" + display_date + '\'' +
                ", display_time='" + display_time + '\'' +
                ", apply_time='" + apply_time + '\'' +
                '}';
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getMeeting_name() {
        return meeting_name;
    }

    public void setMeeting_name(String meeting_name) {
        this.meeting_name = meeting_name;
    }

    public String getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(String applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public Integer getApplicant_date() {
        return applicant_date;
    }

    public void setApplicant_date(Integer applicant_date) {
        this.applicant_date = applicant_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(String display_date) {
        this.display_date = display_date;
    }

    public String getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }
}

