package services;


import entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ProveedorRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public ProveedorEntity guardarProveedor(ProveedorEntity proveedor){
        return proveedorRepository.save(proveedor);
    }

    public Optional<ProveedorEntity> obtenerPorId(Long id){
        return proveedorRepository.findById(id);
    }

    public boolean eliminarProveedor(Long id){
        try {
            proveedorRepository.deleteById(id);
            return true;
        }catch (Exception err){
            return false;
        }
    }

}
