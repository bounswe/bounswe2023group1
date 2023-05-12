package com.a1.disasterresponse.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BookmarkedDestination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}
	
	public BookmarkedDestination() {}

	public BookmarkedDestination(String address) {
		this.address = address;
	}
	
	public BookmarkedDestination(Long id, String address) {
		this.id = id;
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookmarkedDestination other = (BookmarkedDestination) obj;
		return Objects.equals(address, other.address) && Objects.equals(id, other.id);
	}

}
