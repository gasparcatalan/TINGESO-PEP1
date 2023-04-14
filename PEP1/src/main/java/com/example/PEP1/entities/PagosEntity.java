package com.example.PEP1.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class PagosEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private  Long id_pago;


    private  Integer total_acopios;
    private  Integer total ;
    private  String codigo_proveedor ;
    private  Date fecha;


    @ManyToOne
    private ProveedorEntity proveedor;


}
