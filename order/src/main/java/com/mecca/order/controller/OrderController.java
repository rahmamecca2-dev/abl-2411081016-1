package com.mecca.order.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mecca.order.entity.Orders;
import com.mecca.order.service.OrderService;
import com.mecca.order.vo.ResponseTemplateVO;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /api/order -> Buat transaksi order baru
    @PostMapping
    public ResponseEntity<?> simpanOrder(@RequestBody Orders order) {
        try {
            Orders hasil = orderService.simpanOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(hasil);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/order -> Mengambil semua data order lengkap dengan produk dan pelanggan (menggunakan VO)
    // Bisa difilter berdasarkan pelanggan: GET /api/order?pelangganId=1
    @GetMapping
    public List<ResponseTemplateVO> getAllOrders(
            @RequestParam(value = "pelangganId", required = false) Long pelangganId) {
        if (pelangganId != null) {
            return orderService.getOrdersDenganDetailByPelanggan(pelangganId);
        }
        return orderService.getAllOrdersDenganDetail();
    }

    // GET /api/order/{id} -> Mengambil data order saja berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
        Orders order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order tidak ditemukan");
        }
        return ResponseEntity.ok(order);
    }

    // GET /api/order/{id}/detail -> Mengambil detail order lengkap dengan VO (Order + Produk + Pelanggan)
    @GetMapping("/{id}/detail")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") Long id) {
        ResponseTemplateVO detail = orderService.getOrderDenganDetail(id);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order tidak ditemukan");
        }
        return ResponseEntity.ok(detail);
    }

    // PUT /api/order/{id} -> Update order
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody Orders order) {
        try {
            Orders hasil = orderService.updateOrder(id, order);
            if (hasil == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order tidak ditemukan");
            }
            return ResponseEntity.ok(hasil);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/order/{id} -> Hapus order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> hapusOrder(@PathVariable("id") Long id) {
        if (!orderService.hapusOrder(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order tidak ditemukan");
        }
        return ResponseEntity.noContent().build();
    }
}