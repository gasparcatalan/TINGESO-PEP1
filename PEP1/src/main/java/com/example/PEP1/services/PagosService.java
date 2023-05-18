package com.example.PEP1.services;
import com.example.PEP1.entities.LaboratorioEntity;
import com.example.PEP1.entities.PagosEntity;
import com.example.PEP1.entities.ProveedorEntity;
import com.example.PEP1.repositories.AcopioRepository;
import com.example.PEP1.repositories.LaboratorioRepository;
import com.example.PEP1.repositories.ProveedorRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PEP1.repositories.PagosRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
public class PagosService {

    @Autowired
    PagosRepository pagosRepository;
    @Autowired
    AcopioRepository acopioRepository;
    @Autowired
    LaboratorioRepository laboratorioRepository;
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<PagosEntity> obtenerPagos(Long codigo){
        return pagosRepository.findAllByCodigo_proveedor(codigo);}

    public PagosEntity obtenerporID(Long id){
        return  pagosRepository.findporId(id);
    }
    public void generarPagos(){
        ArrayList<ProveedorEntity> proveedores =
                (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
        for (ProveedorEntity proveedor : proveedores){
            crearPago(proveedor.getCodigo());}}


    public void crearPago(Long codigo){
        ProveedorEntity p = proveedorRepository.findProveedorByCodigo(codigo);
        LaboratorioEntity l = laboratorioRepository.findByCodigo(codigo);
        Double klsLeche = acopioRepository.totalLeche(codigo);
        if (klsLeche==null ){klsLeche = 0.0;}
        if (l ==null){  l = new LaboratorioEntity();
                        l = labNulo(codigo);}
        ArrayList<Double> bonos =  bonos(codigo,p.getCategoria());
        ArrayList<Double> variaciones = variaciones(codigo,klsLeche,l.getGrasas(),l.getSolidos());
        PagosEntity pago = new PagosEntity();
        pago = settearPago(p,pago,klsLeche,bonos,variaciones, l);
        calcularTotal(pago);
        pagosRepository.save(pago);}


    public void calcularTotal(PagosEntity pg ){
        Double pagos = pg.getPagoGrasa()+ pg.getPagoCategoria()
                +pg.getPagoSolido();
        pagos = pagos + pagos*(pg.getBonfFrec()/100);
        Double varTotal = pg.getVarTotal();
        Double desc = pagos * (varTotal/100.0);
        Double pagoTotal = pagos - desc ;
        if (pagoTotal < 0.0){pagoTotal = 0.0;}
        pg.setTotal(pagoTotal);
        if(pagoTotal>950000.0){
            pg.setMontoRetencion(pagoTotal * (0.13));
            pg.setMontoFinal(pagoTotal - pg.getMontoRetencion());
            return;}
        pg.setMontoRetencion(0.0);
        pg.setMontoFinal(pagoTotal);}


    public PagosEntity settearPago(ProveedorEntity p,PagosEntity pg,
                                   Double leche, ArrayList<Double> bono,
                                   ArrayList<Double> vars, LaboratorioEntity l){
        //Datos proveedor y total Acopios
        setPago1(p,pg,leche,l);
        //Laboratorio y variaciones
        setPago2(pg, vars);
        //Montos, descuentos otros
        setPago3(pg, bono);
        //Dias de envio y promedio
        setPago4(pg);
        return pg;}


    public void setPago1(ProveedorEntity p, PagosEntity pg,
                         Double leche, LaboratorioEntity l){
        pg.setNombreProveedor(p.getNombre());
        pg.setCodigoProveedor(p.getCodigo());
        pg.setLeche(leche);
        if (l==null){pg.setGrasa(0.0);
                     pg.setSolidos(0.0);}
        else {pg.setGrasa(l.getGrasas());
        pg.setSolidos(l.getSolidos());
        pg.setFecha(obtenerFechaHoy());}}


    public  void setPago2(PagosEntity pg, ArrayList<Double> vars){
        pg.setVarLeche(vars.get(0));
        pg.setVarGrasa(vars.get(1));
        pg.setVarST(vars.get(2));
        pg.setVarTotal(vars.get(3));
        pg.setDescVarLeche(pg.getVarLeche()/100.0);
        pg.setDescVarGrasa(pg.getVarGrasa()/100.0);
        pg.setDescVarSolidos(pg.getVarST()/100.0);}


    public void setPago3(PagosEntity pg,ArrayList<Double> bonos){
        pg.setPagoCategoria(bonos.get(0));
        pg.setPagoGrasa(bonos.get(1));
        pg.setPagoSolido(bonos.get(2));
        pg.setBonfFrec(bonos.get(3));

    }

    public void setPago4(PagosEntity pg){
       Integer turnosM = acopioRepository.countTurno(pg.getCodigoProveedor(), "M");
       Integer turnosT = acopioRepository.countTurno(pg.getCodigoProveedor(), "T");
       if (turnosM>turnosT){pg.setDiasEnvios(turnosM);}
       else {pg.setDiasEnvios(turnosT);}
       Integer turnos = 0;
       turnos =  turnosM + turnosT;
        if (turnos == 0){pg.setPromLecheDia(0.0);
            return;}
        pg.setPromLecheDia(pg.getLeche()/Double.valueOf(turnos));
    }


    public  ArrayList<Double> bonos(Long codigo,    String categoria){
        ArrayList<Double>temp = bonoXLab(codigo, categoria);
        temp.add(bonoFrecuencia(codigo));//[cat,grasa,solido,frec]
        return temp;}


    public  ArrayList<Double> variaciones(Long codigo, Double totalKls,
                                          Double grasa, Double solidos){
        ArrayList<Double> total_var =new ArrayList<>();
        Date hoy = obtenerFechaHoy();
        //[var_leche, var_grasa, var_St, total_var]
        if(pagosRepository.findAnteriorPago(codigo,hoy) == null){
            total_var = variaciones_1(totalKls,grasa,solidos);
        }else{
        total_var = variaciones_2(codigo,totalKls,grasa,solidos);
        }
        //[var_leche, var_grasa, var_St, total_var]}
        return total_var;
    }

    public ArrayList<Double> variaciones_1( Double totalKls,
                                           Double grasa, Double solidos){
        ArrayList<Double> totalVar =new ArrayList<>();
        totalVar.add(varLeche(0.0,totalKls));
        totalVar.add(varGrasa(0.0,grasa));
        totalVar.add(varST(0.0, solidos));
        totalVar.add(totalVar.stream().mapToDouble(d->d).sum());
        return totalVar;
    }
    public ArrayList<Double> variaciones_2(Long codigo, Double totalKls,
                                           Double grasa, Double solidos){
        ArrayList<Double> totalVar =new ArrayList<>();
        Date hoy = obtenerFechaHoy();
        PagosEntity p = pagosRepository.findAnteriorPago(codigo,hoy);
        totalVar.add(varLeche(p.getLeche(),totalKls));
        totalVar.add(varGrasa(p.getGrasa(),grasa));
        totalVar.add(varST(p.getSolidos(), solidos));
        totalVar.add(totalVar.stream().mapToDouble(d->d).sum());
        return totalVar;
    }


    public Double varLeche(Double lechePrev,Double lecheAhora){
            Double dif = lechePrev - lecheAhora;
            if (dif > 0.0){
                Double dif2 = (dif/lecheAhora)*100;
                if (dif2>=0.0 && dif2 <=8.0){ return 0.0;}
                if (dif2>=9.0 && dif2 <=25.0){ return 7.0;}
                if (dif2>=26.0 && dif2 <=45.0){ return 15.0;}
                if (dif2>=46.0){ return 30.0;}}
            return 0.0;}


    public Double varGrasa(Double grasaPrev,Double grasaAhora){
        Double dif = grasaPrev - grasaAhora;
        if (dif > 0.0){
            Double dif2 = (dif/grasaAhora)*100;
            if (dif2>=0.0 && dif2 <=15.0){ return 0.0;}
            if (dif2>=16.0 && dif2 <=25.0){ return 12.0;}
            if (dif2>=26.0 && dif2 <=40.0){ return 20.0;}
            if (dif2>=41.0){ return 30.0;}}
        return 0.0;}


    public Double varST(Double stPrev,Double stAhora){
        Double dif = stPrev - stAhora;
        if (dif > 0.0){
            Double dif2 = (dif/stAhora)*100;
            if (dif2>=0.0 && dif2 <=6.0){ return 0.0;}
            if (dif2>=7.0 && dif2 <=12.0){ return 18.0;}
            if (dif2>=13.0 && dif2 <=35.0){ return 27.0;}
            if (dif2>=36.0){ return 45.0;}}
        return 0.0;}
    public ArrayList<Double> bonoXLab(Long codigo, String categoria){
        Double totalKls = acopioRepository.totalLeche(codigo);
        if (totalKls == null){totalKls = 0.0;}
        LaboratorioEntity l = laboratorioRepository.findByCodigo(codigo);
        if (l == null) { l = new LaboratorioEntity();
                         l= labNulo( codigo);}
        Double bonoCat = bonoCategoria(categoria,totalKls);
        Double bonoGrasa = bonoGrasa(l.getGrasas(),totalKls);
        Double bonoSolido = bonoSolidos(l.getSolidos(), totalKls);
        return new ArrayList<>(Arrays.asList(bonoCat,bonoGrasa,bonoSolido));}
    public Double  bonoCategoria(String categoria , Double totalKls){
        if(categoria.equals("A")){return totalKls * 700.0;}
        if(categoria.equals("B")){return totalKls * 550.0;}
        if(categoria.equals("C")){return totalKls * 400.0;}
        if(categoria.equals("D")){return totalKls * 250.0;}
        return 0.0;}
    public Double bonoGrasa(Double grasa , Double klsLeche){

        if (grasa>=0.0 && grasa <=20.0){ return klsLeche*30.0;}
        if (grasa>=21.0 && grasa <=45.0){ return klsLeche*80.0;}
        if (grasa >=46.0){ return klsLeche*120.0;}
        return 0.0;}
    public Double bonoSolidos(Double solidos , Double klsLeche){
        if (solidos>= 0 && solidos <=7 ){ return klsLeche*-130.0;}
        if (solidos>=8 && solidos <=18){ return klsLeche*-90.0;}
        if (solidos>=19 && solidos<=35){ return klsLeche*95.0;}
        if (solidos >=36){ return klsLeche*150.0;}
        return 0.0;}
    public Double bonoFrecuencia(Long codigo){
        Integer m = acopioRepository.countTurno(codigo, "M");
        Integer t = acopioRepository.countTurno(codigo, "T");
        if (m>= 10 && t>= 10){return 20.0;}
        if (m>=10 ){return 12.0;}
        if (t>=10){return 8.0;}
        return 0.0;}

    public LaboratorioEntity labNulo(Long codigo){
        LaboratorioEntity l = new LaboratorioEntity();
        l.setCodigoProveedor(codigo);
        l.setGrasas(0.0);
        l.setSolidos(0.0);
        return l;
    }
    @Generated
    public Date obtenerFechaHoy(){
        Date hoy = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd");
            return temp.parse(formatter.format(hoy));
        }catch (ParseException ex){
            throw new RuntimeException(ex);}}
    }


