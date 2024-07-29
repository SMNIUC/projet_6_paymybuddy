package com.openclassrooms.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "connection")
public class Connection
{
    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConnection;

    @OneToOne
    @JoinColumn(name = "fk_user_id_1", referencedColumnName = "user_id")
    private User connectedUser;

    @OneToOne
    @JoinColumn(name = "fk_user_id_2", referencedColumnName = "user_id")
    private User addedUser;

}
