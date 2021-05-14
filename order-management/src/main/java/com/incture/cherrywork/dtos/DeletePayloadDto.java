package com.incture.cherrywork.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Data
public class DeletePayloadDto {
	
	public boolean delete;
	 public String id;
	
	public boolean isDelete() {
		return delete;
	}
		public DeletePayloadDto() {
			super();
			
		}
	public DeletePayloadDto(boolean delete, String id) {
			super();
			this.delete = delete;
			this.id = id;
		}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
		
		
		
	

}
