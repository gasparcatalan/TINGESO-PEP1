package com.example.PEP1;

import com.example.PEP1.entities.LaboratorioEntity;
import com.example.PEP1.entities.PagosEntity;
import com.example.PEP1.entities.ProveedorEntity;
import com.example.PEP1.services.LaboratorioService;
import com.example.PEP1.services.PagosService;
import com.example.PEP1.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PagosTest {

    @Autowired
    PagosService pago;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    LaboratorioService laboratorioService;

    ProveedorEntity proveedor = new ProveedorEntity();
    PagosEntity pg = new PagosEntity();
    LaboratorioEntity l = new LaboratorioEntity();


    @Test
    void bonoCategoria(){
    Double kls_leche = 10.0 ;
    Double bono = pago.bonoCategoria("A",kls_leche);
    assertEquals(7000.0, bono,0.0);
    bono =pago.bonoCategoria("B",kls_leche);
    assertEquals(5500.0, bono,0.0);
    bono = pago.bonoCategoria("C", kls_leche);
    assertEquals(4000.0, bono,0.0);
    bono = pago.bonoCategoria("D", kls_leche);
    assertEquals(2500.0, bono,0.0);
    }

    @Test
    void bonoGrasa() {
        Double kls_leche = 100.0;
        Double bono = pago.bonoGrasa(15.0, kls_leche);
        assertEquals(30.0*kls_leche, bono,0.0);

        bono = pago.bonoGrasa(30.0, kls_leche);
        assertEquals( kls_leche * 80.0, bono,0.0);

        Double bono3 = pago.bonoGrasa(50.0, kls_leche);
        assertEquals(kls_leche * 120.0, bono3,0.0);

        Double bono4 = pago.bonoGrasa(-50.0, kls_leche);
        assertEquals(0.0,bono4,0.0);
    }

    @Test
    void bonoSolidosMenorIgualA7() {
        Double solidos = 7.0;
        Double kls_leche = 100.0;
        Double bonoEsperado = kls_leche * -130.0;
        Double bonoResultado = pago.bonoSolidos(solidos, kls_leche);
        assertEquals(bonoEsperado, bonoResultado, 0.01);
    }

    @Test
    void BonoSolidosEntre8Y18() {
        Double solidos = 14.0;
        Double kls_leche = 200.0;
        Double bonoEsperado = kls_leche * -90.0;
        Double bonoResultado = pago.bonoSolidos(solidos, kls_leche);
        assertEquals(bonoEsperado, bonoResultado, 0.01);
    }

    @Test
    void BonoSolidosEntre19Y35() {
        Double solidos = 25.0;
        Double kls_leche = 150.0;
        Double bonoEsperado = kls_leche * 95.0;
        Double bonoResultado = pago.bonoSolidos(solidos, kls_leche);
        assertEquals(bonoEsperado, bonoResultado, 0.01);
    }

    @Test
    void BonoSolidosMayorIgualA36() {
        Double solidos = 40.0;
        Double kls_leche = 50.0;
        Double bonoEsperado = kls_leche * 150.0;
        Double bonoResultado = pago.bonoSolidos(solidos, kls_leche);
        assertEquals(bonoEsperado, bonoResultado, 0.01);
    }

    @Test
    void BonoSolidosFueraDeRango() {
        Double solidos = -1.0;
        Double kls_leche = 50.0;
        Double bonoEsperado = 0.0;
        Double bonoResultado = pago.bonoSolidos(solidos, kls_leche);
        assertEquals(bonoEsperado, bonoResultado, 0.01);
    }

    @Test
     void VarLecheDif2_8() {
        Double leche_prev = 100.0;
        Double leche_ahora = 92.0;
        Double expected = 0.0;
        Double result = pago.varLeche(leche_prev, leche_ahora);
        assertEquals(expected, result);
    }

    @Test
    void VarLeche9a25() {
        Double leche_prev = 100.0;
        Double leche_ahora = 88.0;
        Double expected = 7.0;
        Double result = pago.varLeche(leche_prev, leche_ahora);
        assertEquals(expected, result);
    }

    @Test
    void VarLeche26a45() {
        Double leche_prev = 100.0;
        Double leche_ahora = 75.0;
        Double expected = 15.0;
        Double result = pago.varLeche(leche_prev, leche_ahora);
        assertEquals(expected, result);
    }

    @Test
    void VarLecheDif46() {
        Double leche_prev = 100.0;
        Double leche_ahora = 54.0;
        Double expected = 30.0;
        Double result = pago.varLeche(leche_prev, leche_ahora);
        assertEquals(expected, result);
    }

    @Test
    void VarGrasa0a15() {
        Double grasa_prev = 30.0;
        Double grasa_ahora = 27.0;
        Double expected = 0.0;
        Double result = pago.varGrasa(grasa_prev, grasa_ahora);
        assertEquals(expected, result);
    }

    @Test
     void VarGrasa16a25() {
        Double grasa_prev = 30.0;
        Double grasa_ahora = 25.0;
        Double expected = 12.0;
        Double result = pago.varGrasa(grasa_prev, grasa_ahora);
        assertEquals(expected, result);
    }

    @Test
    void VarGrasa26a40() {
        Double grasa_prev = 30.0;
        Double grasa_ahora = 22.0;
        Double expected = 20.0;
        Double result = pago.varGrasa(grasa_prev, grasa_ahora);
        assertEquals(expected, result);
    }

    @Test
    void VarGrasa41() {
        // Arrange
        Double grasa_prev = 30.0;
        Double grasa_ahora = 17.0;
        Double expected = 30.0;
        Double result = pago.varGrasa(grasa_prev, grasa_ahora);
        assertEquals(expected, result);
    }

    @Test
    void VarST1() {
        Double st_prev = 100.0;
        Double st_ahora = 98.0;
        Double expected = 0.0;
        Double result = pago.varST(st_prev, st_ahora);
        assertEquals(expected, result, 0.0);
    }

    @Test
    void VarST2() {
        Double st_prev = 10.0;
        Double st_ahora = 9.0;
        Double expected = 18.0;
        Double result = pago.varST(st_prev, st_ahora);
        assertEquals(expected, result, 0.0);
    }

    @Test
    void VarST3() {
        Double st_prev = 10.0;
        Double st_ahora = 8.0;
        Double expected = 27.0;
        Double result = pago.varST(st_prev, st_ahora);
        assertEquals(expected, result, 0.0);
    }

    @Test
    void VarST4() {
        Double st_prev = 10.0;
        Double st_ahora = 4.0;
        Double expected = 45.0;
        Double result = pago.varST(st_prev, st_ahora);
        assertEquals(expected, result, 0.0);
    }

    @Test
    void bonoFrec(){
        Double bono =pago.bonoFrecuencia(Long.valueOf(1001));
        assertEquals(20.0,bono,0.0);
        bono = pago.bonoFrecuencia(Long.valueOf(1002));
        assertEquals(12.0, bono, 0.0);
        bono = pago.bonoFrecuencia(Long.valueOf(1003));
        assertEquals(8.0, bono,0.0);
        bono = pago.bonoFrecuencia(Long.valueOf(1004));
        assertEquals(0.0, bono,0.0);

    }

    @Test
    void bonoxLab(){
        ArrayList<Double> bonos = pago.bonoXLab(Long.valueOf(1001),"A");
        ArrayList<Double> esperados = new ArrayList<>();
        esperados.add(133700.0);
        esperados.add(22920.0);
        esperados.add(28650.0);
        assertArrayEquals(esperados.toArray(),bonos.toArray());


    }

    @Test
    void bonos(){
    ArrayList<Double> bonos = pago.bonos(Long.valueOf(1001),"A");
        for (int i = 0; i < bonos.size(); i++) {
            System.out.print("\n");
            System.out.println(bonos.get(i));
        }
    ArrayList<Double> esperados = new ArrayList<>();
    esperados.add(133700.0);
    esperados.add(22920.0);
    esperados.add(28650.0);
    esperados.add(20.0);
    assertArrayEquals(esperados.toArray(),bonos.toArray());

    }

    @Test
    void variaciones(){
        ArrayList<Double> variaciones = pago.variaciones(Long.valueOf(1001), 191.0,46.0,36.0);
        ArrayList<Double> esperados = new ArrayList<>();
        esperados.add(0.0);
        esperados.add(0.0);
        esperados.add(0.0);
        esperados.add(0.0);
        assertArrayEquals(esperados.toArray(),variaciones.toArray());


    }

    @Test
    void setPago1(){
        //SetPagos1
        proveedor = proveedorService.obtenerProveedor(Long.valueOf(1001));
        l = laboratorioService.obtenerLaboratorio(Long.valueOf(1001));
        pago.setPago1(proveedor,pg,191.0,l);
        assertEquals("Gaspar Catalan", pg.getNombre_proveedor());
        assertEquals(Long.valueOf(1001), pg.getCodigo_proveedor());
        assertEquals(191.0, pg.getLeche(),0.0);
        assertEquals(46.0, pg.getGrasa(),0.0);
        assertEquals(36.0, pg.getSolidos(),0.0);

    }

    @Test
    void setPago2(){
        proveedor = proveedorService.obtenerProveedor(Long.valueOf(1001));
        l = laboratorioService.obtenerLaboratorio(Long.valueOf(1001));
        pago.setPago1(proveedor,pg,191.0,l);
        ArrayList<Double> variaciones = pago.variaciones(Long.valueOf(1001), 191.0,46.0,36.0);
        pago.setPago2(pg,variaciones);
        assertEquals(0.0, pg.getVar_leche(),0.0);
        assertEquals(0.0,pg.getVar_grasa(),0.0);
        assertEquals(0.0,pg.getVar_ST(),0.0);
        assertEquals(0.0, pg.getVar_total(),0.0);
        assertEquals(0.0,pg.getDesc_var_leche(),0.0);
        assertEquals(0.0,pg.getDesc_var_grasa(),0.0);
        assertEquals(0.0,pg.getDesc_var_solidos(),0.0);
    }

    @Test
    void setPago3(){
        proveedor = proveedorService.obtenerProveedor(Long.valueOf(1001));
        l = laboratorioService.obtenerLaboratorio(Long.valueOf(1001));
        pago.setPago1(proveedor,pg,191.0,l);
        ArrayList<Double> variaciones = pago.variaciones(Long.valueOf(1001), 191.0,46.0,36.0);
        pago.setPago2(pg,variaciones);
        ArrayList<Double> bonos = pago.bonos(Long.valueOf(1001), "A");
        pago.setPago3(pg,bonos);
        assertEquals(133700.0, pg.getPago_categoria(),0.0);
        assertEquals(22920.0, pg.getPago_grasa(),0.0);
        assertEquals(28650.0, pg.getPago_solido(),0.0);
        assertEquals(20.0, pg.getBonf_frec(),0.0);

    }

    @Test
    void setPago(){
        proveedor = proveedorService.obtenerProveedor(Long.valueOf(1001));
        l = laboratorioService.obtenerLaboratorio(Long.valueOf(1001));
        ArrayList<Double> variaciones = pago.variaciones(Long.valueOf(1001), 191.0,46.0,36.0);
        ArrayList<Double> bonos = pago.bonos(Long.valueOf(1001), "A");
        pago.settearPago(proveedor,pg,191.0,bonos,variaciones,l);


    }


    @Test
    void calculoPago(){
        proveedor = proveedorService.obtenerProveedor(Long.valueOf(1001));
        l = laboratorioService.obtenerLaboratorio(Long.valueOf(1001));
        ArrayList<Double> variaciones = pago.variaciones(Long.valueOf(1001),
                                            191.0,46.0,36.0);
        ArrayList<Double> bonos = pago.bonos(Long.valueOf(1001), "A");
        pago.settearPago(proveedor,pg,191.0,bonos,variaciones,l);
        pago.calcularTotal(pg);
        assertEquals(222324.0, pg.getMonto_final(),0.0);
        assertEquals(0.0, pg.getMonto_retencion(), 0.0);

    }

    @Test
    void calculoPago2(){
        proveedor = proveedorService.obtenerProveedor(Long.valueOf(1003));
        l = laboratorioService.obtenerLaboratorio(Long.valueOf(1003));
        ArrayList<Double> variaciones = pago.variaciones(Long.valueOf(1003),
                1000.0,46.0,36.0);
        ArrayList<Double> bonos = pago.bonos(Long.valueOf(1003), "A");
        pago.settearPago(proveedor,pg,1000.0,bonos,variaciones,l);
        pago.calcularTotal(pg);

    }

    @Test
    void crearPago(){
        pago.crearPago(Long.valueOf(1001));


    }
    @Test
    void allPagos(){
        pago.generarPagos();
    }

}
