package com.mecca.order.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mecca.order.entity.Orders;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"produk", "pelanggan", "id", "jumlah", "pelanggan_id", "produk_id", "tgl_trans", "total"})
public class ResponseTemplateVO {

    private ProdukVO produk;
    private PelangganVO pelanggan;

    private Long id;
    private Integer jumlah;

    @JsonProperty("pelanggan_id")
    private Long pelangganId;

    @JsonProperty("produk_id")
    private Long produkId;

    @JsonProperty("tgl_trans")
    private Object tglTrans;

    private Double total;

    public ResponseTemplateVO() {
    }

    public ResponseTemplateVO(Orders order, PelangganVO pelanggan, ProdukVO produk) {
        this.pelanggan = pelanggan;
        this.produk = produk;
        if (order != null) {
            this.id = order.getId();
            this.jumlah = order.getJumlah();
            this.pelangganId = order.getPelangganId();
            this.produkId = order.getProdukId();
            this.tglTrans = order.getTglTrans();
            this.total = order.getTotal();
        }
    }

    public ProdukVO getProduk() {
        return produk;
    }

    public void setProduk(ProdukVO produk) {
        this.produk = produk;
    }

    public PelangganVO getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(PelangganVO pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Long getPelangganId() {
        return pelangganId;
    }

    public void setPelangganId(Long pelangganId) {
        this.pelangganId = pelangganId;
    }

    public Long getProdukId() {
        return produkId;
    }

    public void setProdukId(Long produkId) {
        this.produkId = produkId;
    }

    public Object getTglTrans() {
        return tglTrans;
    }

    public void setTglTrans(Object tglTrans) {
        this.tglTrans = tglTrans;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ResponseTemplateVO{" +
                "id=" + id +
                ", produk=" + produk +
                ", pelanggan=" + pelanggan +
                ", jumlah=" + jumlah +
                ", pelangganId=" + pelangganId +
                ", produkId=" + produkId +
                ", tglTrans=" + tglTrans +
                ", total=" + total +
                '}';
    }
}