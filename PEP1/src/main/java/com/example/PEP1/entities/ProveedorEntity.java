package com.example.PEP1.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Entity
@Table(name = "proveedores")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ProveedorEntity {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long Id;

    private Long codigo;
    private String nombre;
    private String categoria;
    private String retencion;

}
