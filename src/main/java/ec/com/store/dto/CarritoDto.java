package ec.com.store.dto;

public class CarritoDto<T> {

	private T data;

	public CarritoDto(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
