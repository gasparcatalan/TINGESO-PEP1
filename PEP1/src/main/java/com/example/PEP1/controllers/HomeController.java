package com.example.PEP1.controllers;

import com.example.PEP1.services.AcopioService;
import com.example.PEP1.services.LaboratorioService;
import com.example.PEP1.services.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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





}


