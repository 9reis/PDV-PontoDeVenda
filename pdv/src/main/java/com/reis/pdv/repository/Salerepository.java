package com.reis.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reis.pdv.entity.Sale;

@Repository
public interface Salerepository extends JpaRepository<Sale, Long>{

}
