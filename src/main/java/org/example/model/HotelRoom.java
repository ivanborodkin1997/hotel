package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "hotel_room")
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price")
    private double price;

    @Column(name = "number")
    private int number;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "amount_of_stars")
    private int amountOfStars;

    @OneToMany(mappedBy = "hotelRoom",cascade = CascadeType.ALL)
    private List<GuestRegistration> registrations;


    @Override
    public String toString() {
        return "Комната{" +
                "номер=" + getNumber() +
                ", цена=" + getPrice() +
                ", вместимость=" + getCapacity() +
                ", количество звезд=" + getAmountOfStars() +
                '}';
    }
}