package org.example.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "facility_name")
    private String facilityName;

    @Column(name = "facility_category")
    private String facilityCategory;

    @Column(name = "facility_price")
    private double facilityPrice;

    @Column(name = "facility_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate facilityDateTime;

    @ManyToMany(mappedBy = "facilities")
    private List<GuestRegistration> guestRegistration;

    @Override
    public String toString() {
        return "Услуга{" +
                "название='" + getFacilityName() + '\'' +
                ", цена=" + getFacilityPrice() +
                ", дата заказа услуги=" + getFacilityDateTime() +
                '}';
    }
}
