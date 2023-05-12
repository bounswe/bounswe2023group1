package com.a1.disasterresponse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a1.disasterresponse.model.NasaData;

public interface NasaDataRepository extends JpaRepository<NasaData, Long> {


}

