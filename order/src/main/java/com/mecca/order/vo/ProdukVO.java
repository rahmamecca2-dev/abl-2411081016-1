package com.mecca.order.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdukVO {

    private Long id;
    private String nama;
    private Double harga;
    private String deskripsi;

    @JsonAlias({"idjenis", "idJenis"})
    private Long idJenis;

    private JenisProdukVO jenisProduk;

    public ProdukVO() {
    }

    public ProdukVO(Long id, String nama, Double harga, String deskripsi, Long idJenis, JenisProdukVO jenisProduk) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.idJenis = idJenis;
        this.jenisProduk = jenisProduk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Long getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(Long idJenis) {
        this.idJenis = idJenis;
    }

    public JenisProdukVO getJenisProduk() {
        return jenisProduk;
    }

    public void setJenisProduk(JenisProdukVO jenisProduk) {
        this.jenisProduk = jenisProduk;
    }

    @Override
    public String toString() {
        return "ProdukVO{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                ", deskripsi='" + deskripsi + '\'' +
                ", idJenis=" + idJenis +
                ", jenisProduk=" + jenisProduk +
                '}';
    }
}