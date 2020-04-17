package com.example.model;

public class PeriodicTable implements Comparable<PeriodicTable>{

	Integer position;
	Integer randomWeight;
	String name;
	Double weight;
	String symbol;
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	@Override
	public int compareTo(PeriodicTable o) {
		// TODO Auto-generated method stub
		return this.getRandomWeight() - o.getRandomWeight();
	}
	
	public Integer getRandomWeight() {
		return randomWeight;
	}
	public void setRandomWeight(Integer randomWeight) {
		this.randomWeight = randomWeight;
	}
	
	
}
