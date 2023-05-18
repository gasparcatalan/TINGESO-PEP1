package com.example.PEP1.services;


import com.example.PEP1.entities.AcopioEntity;
import com.example.PEP1.entities.LaboratorioEntity;
import com.example.PEP1.repositories.AcopioRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PEP1.repositories.LaboratorioRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

@Service
public class LaboratorioService {

    @Autowired
    LaboratorioRepository laboratorioRepository;

    public LaboratorioEntity obtenerLaboratorio(Long codigo){
        return (LaboratorioEntity) laboratorioRepository.findByCodigo(codigo);}
    private final Logger logg = (Logger) LoggerFactory.getLogger(LaboratorioService.class);
    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }
    @Generated
    public void guardarData(LaboratorioEntity data){laboratorioRepository.save(data);}

    @Generated
    public void guardarDataDB(String id_proveedor,String grasa,String solidos ){
        LaboratorioEntity newData = new LaboratorioEntity();
        newData.setCodigoProveedor(Long.parseLong(id_proveedor));
        newData.setSolidos(Double.parseDouble(solidos));
        newData.setGrasas(Double.parseDouble(grasa));
        guardarData(newData);
    }

    @Generated
    public String leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        laboratorioRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            return"Archivo leido exitosamente";
        }catch(Exception e){
            return "No se encontro el archivo";
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }
    @Generated
    public void eliminarData(ArrayList<LaboratorioEntity> datas){
        laboratorioRepository.deleteAll(datas);
    }

}
