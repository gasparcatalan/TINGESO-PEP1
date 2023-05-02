package com.example.PEP1;

import com.example.PEP1.entities.ProveedorEntity;
import com.example.PEP1.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class ProveedorTest {

    @Autowired
    ProveedorService proveedorService;
    @Test
    void obtenerProveedores(){
        ArrayList<ProveedorEntity> ps = proveedorService.obtenerProveedores();
    }

    @Test
    void crearProveedor(){
        proveedorService.crearProveedor(Long.valueOf(1234),"Pablo de Rokha",
                                       "C", "No" );}

}
