package com.mecca.pelanggan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mecca.pelanggan.entity.Pelanggan;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {
    List<Pelanggan> findByNamaContainingIgnoreCase(String nama);
}