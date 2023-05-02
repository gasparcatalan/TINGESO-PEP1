package com.example.PEP1.controllers;
import com.example.PEP1.services.AcopioService;
import com.example.PEP1.services.LaboratorioService;
import com.example.PEP1.services.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class PagosController {
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

}
