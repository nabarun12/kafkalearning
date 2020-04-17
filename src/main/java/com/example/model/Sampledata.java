package com.example.model;

public class Sampledata {

	public Sampledata() {
		
	}

	public String name;
	
	public int age;
	
	public Sampledata(String nameIn, int ageIn){
		
		this.name = nameIn;
		this.age = ageIn;
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Sampledata [name=" + name + ", age=" + age + "]";
	}
	
	
}
