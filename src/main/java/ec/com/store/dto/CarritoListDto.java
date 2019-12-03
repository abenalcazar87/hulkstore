package ec.com.store.dto;

import java.util.List;

public class CarritoListDto<T> {

	private List<T> data;

	public CarritoListDto(List<T> data) {
		super();
		this.data = data;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
