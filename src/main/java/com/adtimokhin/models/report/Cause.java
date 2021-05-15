package com.adtimokhin.models.report;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

@Entity
@Table(name = "table_cause")
public class Cause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cause_title")
    private String title;

    @OneToMany(mappedBy = "cause")
    private List<Report> reports;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
