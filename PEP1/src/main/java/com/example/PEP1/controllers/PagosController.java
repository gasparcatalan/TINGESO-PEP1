package com.example.PEP1.controllers;
import org.springframework.ui.Model;
import com.example.PEP1.entities.ProveedorEntity;
import com.example.PEP1.entities.PagosEntity;
import com.example.PEP1.services.AcopioService;
import com.example.PEP1.services.LaboratorioService;
import com.example.PEP1.services.PagosService;
import com.example.PEP1.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PagosController {
    @Autowired
    ProveedorService proveedorService;

    @Autowired
    PagosService pagosService;
    @Autowired
    LaboratorioService laboratorioService;
    @Autowired
    AcopioService acopioService;


    @GetMapping("/importLab")
    public String mostrarSubirData(){return "importLab";}

    @PostMapping("/importLab")
    public String subirData(@RequestParam ("acopios")MultipartFile acopio,
                            @RequestParam ("laboratorios")MultipartFile laboratorios,
                            RedirectAttributes redirectAttributes){
    acopioService.guardar(acopio);
    laboratorioService.guardar(laboratorios);
    String pathAcopio = acopioService.leerCsv("acopio.csv");
    String pathLab = laboratorioService.leerCsv("Resultados.csv");
    redirectAttributes.addFlashAttribute("pathAcopio", pathAcopio);
    redirectAttributes.addFlashAttribute("pathLab", pathLab);
    pagosService.generarPagos();
    return "index";


    }
    @GetMapping("/pagos/{codigo}")
    public String listarPagos(@PathVariable("codigo") Long codigo, Model model) {
        ProveedorEntity proveedor = proveedorService.obtenerProveedor(codigo);
        ArrayList<PagosEntity> pagos = pagosService.obtenerPagos(codigo);
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("pagos", pagos);
        return "pagos";
    }

    @GetMapping("/pago/{id}")
    public String mostrarPago(@PathVariable("id") Long id, Model model) {
        PagosEntity pago = pagosService.obtenerporID(id);
        model.addAttribute("pago", pago);
        return "pago";
    }



}
