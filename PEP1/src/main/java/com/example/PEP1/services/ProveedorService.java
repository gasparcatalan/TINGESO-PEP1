package com.example.PEP1.services;


import com.example.PEP1.entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PEP1.repositories.ProveedorRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();}
    public  ProveedorEntity obtenerProveedor(Long codigo){
        return  (ProveedorEntity) proveedorRepository.findProveedorByCodigo(codigo);
    }


    public void crearProveedor(Long codigo,String nombre, String categoria, String retencion) {
        ProveedorEntity temp_proveedor = new ProveedorEntity();
        temp_proveedor.setCodigo(codigo);
        temp_proveedor.setNombre(nombre);
        temp_proveedor.setCategoria(categoria);
        temp_proveedor.setRetencion(retencion);
        proveedorRepository.save(temp_proveedor);
    }



}
