package com.mecca.product.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mecca.product.entity.JenisProduk;

@Repository
public interface JenisProdukRepository extends JpaRepository<JenisProduk, Long> {
}
