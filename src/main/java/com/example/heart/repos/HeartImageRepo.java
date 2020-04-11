package com.example.heart.repos;

import com.example.heart.domain.HeartImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HeartImageRepo extends JpaRepository<HeartImage, Long>{

    List<HeartImage> findByName(String name);
    List<HeartImage> findById(int id);

}
