package com.incture.cherrywork.dtos;



public class Group {

	private String display;
	private String value;
	private String $ref;

	public Group() {

	}

	public Group(String display, String value, String $ref) {
		super();
		this.display = display;
		this.value = value;
		this.$ref = $ref;
	}

	@Override
	public String toString() {
		return "Group [display=" + display + ", value=" + value + ", $ref=" + $ref + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (($ref == null) ? 0 : $ref.hashCode());
		result = prime * result + ((display == null) ? 0 : display.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if ($ref == null) {
			if (other.$ref != null)
				return false;
		} else if (!$ref.equals(other.$ref))
			return false;
		if (display == null) {
			if (other.display != null)
				return false;
		} else if (!display.equals(other.display))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getDisplay() {
		return display;
	}

	public String getValue() {
		return value;
	}

	public String get$ref() {
		return $ref;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void set$ref(String $ref) {
		this.$ref = $ref;
	}

}
