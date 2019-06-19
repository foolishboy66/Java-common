package com.foolishboy.domain;

import com.alibaba.fastjson.annotation.JSONField;

public class Action {

	private String name;
	
	@JSONField(serialize = false)
	private User user;
	
	private Action nextAction;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Action getNextAction() {
		return nextAction;
	}

	public void setNextAction(Action nextAction) {
		this.nextAction = nextAction;
	}
	
}
