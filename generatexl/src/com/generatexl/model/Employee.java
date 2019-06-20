package com.generatexl.model;

public class Employee {
    private String name;
    private String email;
    private double salary;

    public Employee(String name, String email,  double salary) {
        this.name = name;
        this.email = email;
       
        this.salary = salary;
    }

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
    
}