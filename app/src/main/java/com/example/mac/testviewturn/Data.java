package com.example.mac.testviewturn;

public class Data {

	private String name;
	private float detial;

	public Data(String pname, float pdetial) {
		setDetial(pdetial);
		setName(pname);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDetial() {
		return detial;
	}

	public void setDetial(float detial) {
		this.detial = detial;
	}

}
