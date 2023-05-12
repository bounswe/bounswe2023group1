package com.a1.disasterresponse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IpList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String address) {
		this.ip = address;
	}

	public Long getId() {
		return id;
	}
	
	public IpList() {}

	public IpList(String ip) {
		this.ip = ip;
	}

}
