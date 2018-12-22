package com.foolishboy.domain;

public class School {

	private String schoolName;
	private String location;
	private boolean excellent;

	public School() {
	}

	public School(String schoolName, String location, boolean excellent) {
		super();
		this.schoolName = schoolName;
		this.location = location;
		this.excellent = excellent;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isExcellent() {
		return excellent;
	}

	public void setExcellent(boolean excellent) {
		this.excellent = excellent;
	}

	@Override
	public String toString() {
		return "School [schoolName=" + schoolName + ", location=" + location + ", excellent=" + excellent + "]";
	}
}
