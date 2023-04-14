package com.example.PEP1.controllers;

import com.example.PEP1.entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.PEP1.services.ProveedorService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping

public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping ("/lista")
    public String listar(Model model){
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "lista";

    }

    @GetMapping("/crear")
    public  String mostrarCrear(){return "crear";}

    @PostMapping("/crear")
    public String crearProveedor(@RequestParam ("codigo") Long codigo,
                                 @RequestParam ("nombre") String nombre ,
                                 @RequestParam ("categoria") String categoria,
                                 @RequestParam ("retencion") String retencion){
        proveedorService.crearProveedor(codigo,nombre,categoria,retencion);
        return "crear";

    }

}
