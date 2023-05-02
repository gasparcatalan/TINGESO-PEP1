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

    private  Long codigo_proveedor ;
    private String nombre_proveedor;
    private  Date fecha;
    private Integer dias_envios;
    private Double prom_leche_dia;
    //Resultados Lab
    private Double leche;
    private Double grasa;
    private Double solidos;

    //Variaciones
    private Double var_leche;
    private Double var_grasa;
    private Double var_ST;
    private Double var_total;
    //Pagos
    private Double pago_categoria;
    private Double pago_grasa;
    private Double pago_solido;
    private Double bonf_frec;
    private Double desc_var_leche;
    private Double desc_var_grasa;
    private Double desc_var_solidos;
    private Double total;
    private Double monto_retencion;
    private Double monto_final;



}
