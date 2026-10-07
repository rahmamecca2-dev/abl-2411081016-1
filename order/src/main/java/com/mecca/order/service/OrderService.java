package com.mecca.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mecca.order.entity.Orders;
import com.mecca.order.repository.OrderRepository;
import com.mecca.order.vo.JenisProdukVO;
import com.mecca.order.vo.PelangganVO;
import com.mecca.order.vo.ProdukVO;
import com.mecca.order.vo.ResponseTemplateVO;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.produk.url:http://localhost:8080/api/produk}")
    private String produkServiceUrl;

    @Value("${service.pelanggan.url:http://localhost:8081/api/pelanggan}")
    private String pelangganServiceUrl;

    // ==========================================
    // HTTP CLIENT: Mengambil data via URL Service
    // ==========================================

    /**
     * Mengambil data pelanggan dari Pelanggan Service via REST API URL
     */
    public PelangganVO getPelangganData(Long pelangganId) {
        if (pelangganId == null) {
            return null;
        }
        try {
            String url = pelangganServiceUrl + "/" + pelangganId;
            return restTemplate.getForObject(url, PelangganVO.class);
        } catch (Exception e) {
            System.err.println("Gagal mengambil data pelanggan ID " + pelangganId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Mengambil data produk dari Produk Service via REST API URL
     */
    public ProdukVO getProdukData(Long produkId) {
        if (produkId == null) {
            return null;
        }
        try {
            String url = produkServiceUrl + "/" + produkId;
            return restTemplate.getForObject(url, ProdukVO.class);
        } catch (Exception e) {
            System.err.println("Gagal mengambil data produk ID " + produkId + ": " + e.getMessage());
            return null;
        }
    }

    // ==========================================
    // OPERASI BISNIS & CRUD ORDER
    // ==========================================

    /**
     * Menyimpan order baru dengan validasi data pelanggan dan produk
     */
    public Orders simpanOrder(Orders order) {
        // Validasi keberadaan pelanggan
        PelangganVO pelanggan = getPelangganData(order.getPelangganId());
        if (pelanggan == null || pelanggan.getId() == null) {
            throw new IllegalArgumentException("Pelanggan dengan ID " + order.getPelangganId() + " tidak ditemukan!");
        }

        // Validasi keberadaan produk
        ProdukVO produk = getProdukData(order.getProdukId());
        if (produk == null || produk.getId() == null) {
            throw new IllegalArgumentException("Produk dengan ID " + order.getProdukId() + " tidak ditemukan!");
        }

        // Set tanggal transaksi jika belum diisi
        if (order.getTglTrans() == null) {
            order.setTglTrans(LocalDate.now());
        }

        // Hitung total harga otomatis
        double harga = (produk.getHarga() != null) ? produk.getHarga() : 0.0;
        int jumlah = (order.getJumlah() != null) ? order.getJumlah() : 1;
        order.setTotal(harga * jumlah);

        return orderRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Orders> getOrdersByPelangganId(Long pelangganId) {
        return orderRepository.findByPelangganId(pelangganId);
    }

    public Orders updateOrder(Long id, Orders orderBaru) {
        Orders orderLama = orderRepository.findById(id).orElse(null);
        if (orderLama == null) {
            return null;
        }

        // Validasi pelanggan
        PelangganVO pelanggan = getPelangganData(orderBaru.getPelangganId());
        if (pelanggan == null || pelanggan.getId() == null) {
            throw new IllegalArgumentException("Pelanggan dengan ID " + orderBaru.getPelangganId() + " tidak ditemukan!");
        }

        // Validasi produk
        ProdukVO produk = getProdukData(orderBaru.getProdukId());
        if (produk == null || produk.getId() == null) {
            throw new IllegalArgumentException("Produk dengan ID " + orderBaru.getProdukId() + " tidak ditemukan!");
        }

        orderLama.setPelangganId(orderBaru.getPelangganId());
        orderLama.setProdukId(orderBaru.getProdukId());
        orderLama.setJumlah(orderBaru.getJumlah());
        if (orderBaru.getTglTrans() != null) {
            orderLama.setTglTrans(orderBaru.getTglTrans());
        }

        double harga = (produk.getHarga() != null) ? produk.getHarga() : 0.0;
        int jumlah = (orderBaru.getJumlah() != null) ? orderBaru.getJumlah() : 1;
        orderLama.setTotal(harga * jumlah);

        return orderRepository.save(orderLama);
    }

    public boolean hapusOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    // ==========================================
    // VIRTUAL OBJECT (VO): ResponseTemplateVO
    // ==========================================

    /**
     * Mengambil detail satu order lengkap dengan data PelangganVO dan ProdukVO
     */
    public ResponseTemplateVO getOrderDenganDetail(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        return gabungkanData(order);
    }

    /**
     * Mengambil semua order lengkap dengan data PelangganVO dan ProdukVO
     */
    public List<ResponseTemplateVO> getAllOrdersDenganDetail() {
        List<Orders> ordersList = orderRepository.findAll();
        List<ResponseTemplateVO> responseList = new ArrayList<>();
        for (Orders order : ordersList) {
            responseList.add(gabungkanData(order));
        }
        return responseList;
    }

    /**
     * Mengambil order berdasarkan pelangganId lengkap dengan data PelangganVO dan ProdukVO
     */
    public List<ResponseTemplateVO> getOrdersDenganDetailByPelanggan(Long pelangganId) {
        List<Orders> ordersList = orderRepository.findByPelangganId(pelangganId);
        List<ResponseTemplateVO> responseList = new ArrayList<>();
        for (Orders order : ordersList) {
            responseList.add(gabungkanData(order));
        }
        return responseList;
    }

    /**
     * Method pembantu untuk menggabungkan entity Orders dengan PelangganVO dan ProdukVO
     */
    private ResponseTemplateVO gabungkanData(Orders order) {
        PelangganVO pelanggan = getPelangganData(order.getPelangganId());
        ProdukVO produk = getProdukData(order.getProdukId());
        return new ResponseTemplateVO(order, pelanggan, produk);
    }
}