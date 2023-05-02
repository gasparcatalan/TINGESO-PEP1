package com.example.PEP1.controllers;

import com.example.PEP1.services.AcopioService;
import com.example.PEP1.services.LaboratorioService;
import com.example.PEP1.services.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    PagosService pagosService;
    @Autowired
    LaboratorioService laboratorioService;
    @Autowired
    AcopioService acopioService;


    @GetMapping ("/home")
    public String homear(){return "index";}

    @GetMapping("/importLab")
    public String mostrarSubirData(){return "importLab";}
    @PostMapping("/importLab")
    public String subirData(@RequestParam("acopios") MultipartFile acopios,
                            @RequestParam ("laboratorios") MultipartFile laboratorios,
                            RedirectAttributes redirectAttributes){
        acopioService.guardar(acopios);
        laboratorioService.guardar(laboratorios);
        String pathAcopio = acopioService.leerCsv("Acopio.csv");
        String pathLab = laboratorioService.leerCsv("Resultados.csv");
        redirectAttributes.addFlashAttribute("pathAcopio", pathAcopio);
        redirectAttributes.addFlashAttribute("pathLab", pathLab);
        pagosService.generarPagos();
        return "index";


    }



}


