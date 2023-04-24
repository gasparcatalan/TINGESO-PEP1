package com.example.PEP1.services;



import com.example.PEP1.entities.LaboratorioEntity;
import com.example.PEP1.entities.PagosEntity;
import com.example.PEP1.entities.ProveedorEntity;
import com.example.PEP1.repositories.AcopioRepository;
import com.example.PEP1.repositories.LaboratorioRepository;
import com.example.PEP1.repositories.ProveedorRepository;
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
    public void crearPago(Long codigo, Date fecha){
        ProveedorEntity p = proveedorRepository.findProveedorByCodigo(codigo);
        LaboratorioEntity l = laboratorioRepository.findByCodigo(codigo);
        Double kls_leche = acopioRepository.totalLeche(codigo, fecha);
        ArrayList<Double> bonos = new ArrayList<>();
        ArrayList<Double> variaciones = new ArrayList<>();
        bonos = bonos(codigo, fecha,p.getCategoria());
        variaciones = variaciones(codigo,kls_leche,l.getGrasas(),l.getSolidos());
        PagosEntity pago = new PagosEntity();
        pago = settearPago(p,pago,kls_leche,bonos,variaciones, l);
        calcularTotal(pago);

    }
    public void calcularTotal(PagosEntity pg ){
        Double pagos = pg.getPago_grasa()+ pg.getPago_categoria()
                +pg.getPago_solido()+pg.getBonf_frec();
        Double var_total = pg.getVar_total();
        Double desc = pagos * (var_total/100.0);
        Double pago_total = pagos - desc ;
        pg.setTotal(pago_total);
        if(pago_total>950000.0){
            pg.setMonto_retencion(pago_total * (0.13));
            pago_total = pago_total - pg.getMonto_retencion();
            pg.setMonto_final(pago_total);
            return;
        }
        pg.setMonto_retencion(0.0);
        pg.setMonto_final(pago_total);
    }

    public PagosEntity settearPago(ProveedorEntity p,PagosEntity pg,
                                   Double leche, ArrayList<Double> bono,
                                   ArrayList<Double> vars, LaboratorioEntity l){
        //Datos proveedor y total Acopios
        setPago1(p,pg,leche,l);
        //Laboratorio y variaciones
        setPago2(pg, vars);
        //Montos, descuentos otros
        setPago3(pg, bono);
        return pg;
    }

    public void setPago1(ProveedorEntity p, PagosEntity pg,
                         Double leche, LaboratorioEntity l){
        pg.setNombre_proveedor(p.getNombre());
        pg.setCodigo_proveedor(p.getCodigo());
        pg.setLeche(leche);
        pg.setGrasa(l.getGrasas());
        pg.setSolidos(l.getSolidos());
    }
    public  void setPago2(PagosEntity pg, ArrayList<Double> vars){
        pg.setVar_leche(vars.get(0));
        pg.setVar_grasa(vars.get(1));
        pg.setVar_ST(vars.get(2));
        pg.setVar_total(vars.get(3));
        pg.setDesc_var_leche(pg.getVar_leche()/100.0);
        pg.setDesc_var_grasa(pg.getVar_grasa()/100.0);
        pg.setDesc_var_solidos(pg.getDesc_var_solidos()/100.0);
    }

    public void setPago3(PagosEntity pg,ArrayList<Double> bonos){
        pg.setPago_categoria(bonos.get(0));
        pg.setPago_grasa(bonos.get(1));
        pg.setPago_solido(bonos.get(2));
        pg.setBonf_frec(bonos.get(3));
    }
    public  ArrayList<Double> bonos(Long codigo, Date fecha, String categoria){
        ArrayList<Double>temp = bonoXLab(codigo,fecha,categoria);
        temp.add(bonoFrecuencia(codigo));//[cat,grasa,solido,frec]
        return temp;
    }
    public  ArrayList<Double> variaciones(Long codigo, Double total_kls,
                                          Double grasa, Double solidos){
        ArrayList<Double> total_var =new ArrayList<>();
        Date hoy = obtenerFechaHoy();
        PagosEntity p = pagosRepository.findAnteriorPago(codigo,hoy);
        total_var.add(varLeche(p.getLeche(),total_kls));
        total_var.add(varGrasa(p.getGrasa(),grasa));
        total_var.add(varST(p.getSolidos(), solidos));
        total_var.add(total_var.stream().mapToDouble(d->d).sum());
        return total_var;//[var_leche, var_grasa, var_St, total_var]

    }

    public Double varLeche(Double leche_prev,Double leche_ahora){
            Double dif = leche_prev - leche_ahora;
            if (dif < 0.0){
                Double dif2 = (dif/leche_ahora)*100;
                if (dif2>=0.0 && dif2 <=8.0){ return 0.0;}
                if (dif2>=9.0 && dif2 <=25.0){ return 7.0;}
                if (dif2>=26.0 && dif2 <=45.0){ return 15.0;}
                if (dif2>=46.0){ return 30.0;}
            }
            //Cualquier otro caso
            return 0.0;

    }

    public Double varGrasa(Double grasa_prev,Double grasa_ahora){
        Double dif = grasa_prev - grasa_ahora;
        if (dif < 0.0){
            Double dif2 = (dif/grasa_ahora)*100;
            if (dif2>=0.0 && dif2 <=15.0){ return 0.0;}
            if (dif2>=16.0 && dif2 <=25.0){ return 12.0;}
            if (dif2>=26.0 && dif2 <=40.0){ return 20.0;}
            if (dif2>=41.0){ return 30.0;}
        }
        //Cualquier otro caso
        return 0.0;

    }

    public Double varST(Double st_prev,Double st_ahora){
        Double dif = st_prev - st_ahora;
        if (dif < 0.0){
            Double dif2 = (dif/st_ahora)*100;
            if (dif2>=0.0 && dif2 <=6.0){ return 0.0;}
            if (dif2>=7.0 && dif2 <=12.0){ return 18.0;}
            if (dif2>=13.0 && dif2 <=35.0){ return 27.0;}
            if (dif2>=36.0){ return 45.0;}
        }
        //Cualquier otro caso
        return 0.0;

    }

    public ArrayList<Double> bonoXLab(Long codigo, Date fecha,String categoria){
        Double total_kls = acopioRepository.totalLeche(codigo,fecha);
        LaboratorioEntity l = laboratorioRepository.findByCodigo(codigo);
        Double bono_cat = bonoCategoria(categoria,total_kls);
        Double bono_grasa = bonoGrasa(l.getGrasas(),total_kls);
        Double bono_solido = bonoSolidos(l.getSolidos(), total_kls);
        return new ArrayList<>(Arrays.asList(bono_cat,bono_grasa,bono_solido));


    }

    public Double  bonoCategoria(String categoria , Double total_kls){
        if(categoria.equals("A")){return total_kls * 700.0;}
        if(categoria.equals("B")){return total_kls * 550.0;}
        if(categoria.equals("C")){return total_kls * 400.0;}
        if(categoria.equals("D")){return total_kls * 250.0;}
        return 0.0;
        }

    public Double bonoGrasa(Double grasa , Double kls_leche){

        if (grasa>0 && grasa <=20){ return kls_leche*30.0;}
        if (grasa>=21 && grasa <=45){ return kls_leche*80.0;}
        if (grasa >=46){ return kls_leche*120.0;}
        return 0.0;
    }

    public Double bonoSolidos(Double solidos , Double kls_leche){

        if (solidos> 0 && solidos <=7 ){ return kls_leche*-130.0;}
        if (solidos>=8 && solidos <=18){ return kls_leche*-90.0;}
        if (solidos>=19 && solidos<=35){ return kls_leche*95.0;}
        if (solidos >=36){ return kls_leche*150.0;}
        return 0.0;
    }

    public Double bonoFrecuencia(Long codigo){
        Integer m = acopioRepository.countTurno(codigo, "M");
        Integer t = acopioRepository.countTurno(codigo, "T");
        if (m>= 10 && t>= 10){return 20.0;}
        if (m>=10 ){return 12.0;}
        if (t>=10){return 8.0;}
        return 0.0;

    }

    public Date obtenerFechaHoy(){
        Date hoy = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            SimpleDateFormat temp = new SimpleDateFormat("yyyy/MM/dd");
            return temp.parse(formatter.format(hoy));

        }catch (ParseException ex){
            throw new RuntimeException(ex);}
    }
    }


