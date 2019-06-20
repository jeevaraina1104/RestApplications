package com.generatexl.model;

public class BulkEmployee {
	
	private double employeeId;
    private String firstname;
    private String lastname;
    private String gender;
    private String mobile;
    private String location_city;
    private String location_country;
    private String company;
    private String email;
    private double salary;

    public BulkEmployee(double employeeId,String first_name,String last_name,String gender,String mobile, String email, String location_city,String location_country,String company, double salary) {
        this.firstname = first_name;
        this.email=email;
        
       this.company= company;
       this.employeeId=employeeId;
       this.gender=gender;
       this.lastname = last_name;
       this.location_city =location_city;
       this.location_country = location_country;
       this.mobile = mobile;
       
        this.salary = salary;
    }

	public BulkEmployee() {
		// TODO Auto-generated constructor stub
	}

	public double getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(double employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirst_name() {
		return firstname;
	}

	public void setFirst_name(String first_name) {
		this.firstname = first_name;
	}

	public String getLast_name() {
		return lastname;
	}

	public void setLast_name(String last_name) {
		this.lastname = last_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLocation_city() {
		return location_city;
	}

	public void setLocation_city(String location_city) {
		this.location_city = location_city;
	}

	public String getLocation_country() {
		return location_country;
	}

	public void setLocation_country(String location_country) {
		this.location_country = location_country;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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
