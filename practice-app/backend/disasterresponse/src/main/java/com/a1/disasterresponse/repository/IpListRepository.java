package com.a1.disasterresponse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a1.disasterresponse.model.IpList;

public interface IpListRepository extends JpaRepository<IpList, Long> {
    IpList getByIp(String ip);
}
