package com.example.PEP1.services;

import com.example.PEP1.entities.AcopioEntity;
import com.example.PEP1.entities.PagosEntity;
import com.example.PEP1.entities.ProveedorEntity;
import com.example.PEP1.repositories.AcopioRepository;
import com.example.PEP1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PEP1.repositories.PagosRepository;

import java.util.ArrayList;
import java.util.Date;

@Service
public class PagosService {

    @Autowired
    PagosRepository pagosRepository;
    @Autowired
    AcopioRepository acopioRepository;

    @Autowired
    ProveedorRepository proveedorRepository;
    public PagosEntity realizarPago(Long id){
        PagosEntity pago = new PagosEntity();
        return pago;
    }

    public Double  bonificacionCategoria(Long codigo , Date fecha){
        ArrayList<AcopioEntity> acopios = acopioRepository.findAllByCodigo_proveedorAAndFecha(codigo, fecha);
        Double total_kls = acopioRepository.totalLeche(codigo,fecha);
        ProveedorEntity p = proveedorRepository.findProveedorByCodigo(codigo);
        String c = p.getCategoria();
        if(c == "A"){return total_kls * 700.0;}
        if(c == "B"){return total_kls * 550.0;}
        if(c == "C"){return total_kls * 400.0;}
        if(c == "D"){return total_kls * 250.0;}
        else {return 0.0;}
        }

    public Double bonificacionGrasa(Double grasa , Double kls_leche){

        if (grasa>0 && grasa <=20){ return kls_leche*30.0;}
        if (grasa>=21 && grasa <=45){ return kls_leche*80.0;}
        if (grasa >=46){ return kls_leche*120.0;}
        else{return 0.0;}
    }

    public Double bonificacionSolidos(Double solidos , Double kls_leche){

        if (solidos>0 && solidos <=7){ return kls_leche*-130.0;}
        if (solidos>=8 && solidos <=18){ return kls_leche*-90.0;}
        if (solidos >=19 && solidos<=35){ return kls_leche*95.0;}
        if (solidos >=36){ return kls_leche*150.0;}
        else{return 0.0;}
    }
    }


