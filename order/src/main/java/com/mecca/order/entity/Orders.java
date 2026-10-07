package com.mecca.order.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("produk_id")
    @JsonAlias({"produkId", "produk_id"})
    private Long produkId;

    @JsonProperty("pelanggan_id")
    @JsonAlias({"pelangganId", "pelanggan_id"})
    private Long pelangganId;

    @JsonProperty("tgl_trans")
    @JsonAlias({"tglTrans", "tgl_trans"})
    private LocalDate tglTrans;

    private Integer jumlah;
    private Double total;

    public Orders() {
    }

    public Orders(Long id, Long produkId, Long pelangganId, LocalDate tglTrans, Integer jumlah, Double total) {
        this.id = id;
        this.produkId = produkId;
        this.pelangganId = pelangganId;
        this.tglTrans = tglTrans;
        this.jumlah = jumlah;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdukId() {
        return produkId;
    }

    public void setProdukId(Long produkId) {
        this.produkId = produkId;
    }

    public Long getPelangganId() {
        return pelangganId;
    }

    public void setPelangganId(Long pelangganId) {
        this.pelangganId = pelangganId;
    }

    public LocalDate getTglTrans() {
        return tglTrans;
    }

    public void setTglTrans(LocalDate tglTrans) {
        this.tglTrans = tglTrans;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}