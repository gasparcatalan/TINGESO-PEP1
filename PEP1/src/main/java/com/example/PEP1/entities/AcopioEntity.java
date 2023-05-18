package com.example.PEP1.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "acopios")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class AcopioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private Long idAcopio;

    private Double klsLeche;
    private String turno;
    private Long codigoProveedor;
    private Date fecha;


}
