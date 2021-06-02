package com.incture.cherrywork.dto.new_workflow;


import lombok.Data;

public @Data class TaskItemDto {
	
	String taskSerId;
	String itemId;
	public String getTaskSerId() {
		return taskSerId;
	}
	public void setTaskSerId(String taskSerId) {
		this.taskSerId = taskSerId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
