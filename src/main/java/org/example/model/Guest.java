package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "guest",cascade = CascadeType.ALL)
    private List<GuestRegistration> registrations;

    @Override
    public String toString() {
        return "Гость{" +
                "фамилия='" + getSurname() + '\'' +
                ", имя='" + getName() + '\'' +
                '}';
    }
}