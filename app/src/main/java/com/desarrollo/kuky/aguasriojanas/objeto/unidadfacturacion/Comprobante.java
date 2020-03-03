package com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion;

public class Comprobante {
    private String tipoComprobante;
    private String prenumeracion; //pre_com
    private int numeroComprobante;
    private String vencimiento1; //fec_vto1
    private String estado; //sit_com_moroso
    private String periodo; //per_hasta
    private double importe; //imp_tot
    private String vencimiento2;
    private double recargo;
    private double recargoIva;
    private boolean imprimible;

    public Comprobante() {
        // INICIALIZO EN 0 A TODO PARA QUE NO TIRE ERROR AL QUERER MOSTRAR LOS DATOS,
        // EN CASO DE QUE NO LOS LLENE AL RECORRER LA BASE DE DATOS
        this.tipoComprobante = "";
        this.prenumeracion = "";
        this.numeroComprobante = 0;
        this.vencimiento1 = "----/--/--";
        this.estado = "";
        this.periodo = "";
        this.importe = 0;
        this.vencimiento2 = "----/--/--";
        this.recargo = 0;
        this.recargoIva = 0;
        this.imprimible = false;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getPrenumeracion() {
        return prenumeracion;
    }

    public void setPrenumeracion(String prenumeracion) {
        this.prenumeracion = prenumeracion;
    }

    public int getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(int numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public String getVencimiento1() {
        return vencimiento1;
    }

    public void setVencimiento1(String vencimiento1) {
        this.vencimiento1 = vencimiento1;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getVencimiento2() {
        return vencimiento2;
    }

    public void setVencimiento2(String vencimiento2) {
        this.vencimiento2 = vencimiento2;
    }

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

    public double getRecargoIva() {
        return recargoIva;
    }

    public void setRecargoIva(double recargoIva) {
        this.recargoIva = recargoIva;
    }

    public boolean isImprimible() {
        return imprimible;
    }

    public void setImprimible(boolean imprimible) {
        this.imprimible = imprimible;
    }
}
