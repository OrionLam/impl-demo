package me.biz;

import java.io.Serializable;

public class MockDTO implements Serializable {
	private static final long serialVersionUID = 2452846352359458128L;
	private String pk;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	
}
