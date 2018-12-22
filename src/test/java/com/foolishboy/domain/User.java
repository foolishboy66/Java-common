package com.foolishboy.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class User {

	private String name;
	private String email;
	private int age;
	private boolean sex;
	private Date birthDay;
	private Boolean hasChildren;
	private List<String> nickNames;
	private Map<String, School> schoolMap;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<String> getNickNames() {
		return nickNames;
	}

	public void setNickNames(List<String> nickNames) {
		this.nickNames = nickNames;
	}

	public Map<String, School> getSchoolMap() {
		return schoolMap;
	}

	public void setSchoolMap(Map<String, School> schoolMap) {
		this.schoolMap = schoolMap;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", age=" + age + ", sex=" + sex + ", birthDay=" + birthDay
				+ ", hasChildren=" + hasChildren + ", nickNames=" + nickNames + ", schoolMap=" + schoolMap + "]";
	}
	
}
