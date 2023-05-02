package com.example.PEP1.services;


import com.example.PEP1.entities.AcopioEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PEP1.repositories.AcopioRepository;
import org.springframework.web.multipart.MultipartFile;

import lombok.Generated;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import  java.text.SimpleDateFormat;


@Service
public class AcopioService {

    @Autowired
    AcopioRepository acopioRepository;

  private final Logger logg = (Logger) LoggerFactory.getLogger(AcopioService.class);
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
    public void guardarData(AcopioEntity data){
        acopioRepository.save(data);
    }

    @Generated
    public void guardarDataDB(String fecha, String turno, String id_proveedor, String kls_leche){
        AcopioEntity newData = new AcopioEntity();
        try{
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha_temp = temp.parse(fecha);
            newData.setFecha(fecha_temp);
        }catch (ParseException ex){
            throw new RuntimeException(ex);
        }
        newData.setTurno(turno);
        newData.setCodigo_proveedor(Long.parseLong(id_proveedor));
        newData.setKls_leche(Double.parseDouble(kls_leche));
        guardarData(newData);
    }
    public void eliminarData(ArrayList<AcopioEntity> datas){
        acopioRepository.deleteAll(datas);
    }

    @Generated
    public String leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        acopioRepository.deleteAll();
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
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            return "Archivo leido exitosamente";
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
}

