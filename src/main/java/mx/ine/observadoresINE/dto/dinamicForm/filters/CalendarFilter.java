package mx.ine.observadoresINE.dto.dinamicForm.filters;

import java.util.Date;

import mx.ine.observadoresINE.dto.dinamicForm.DFilter;

public class CalendarFilter<D> extends DFilter<D, Date> {

	public CalendarFilter(String id, String name) {
		super(id, name , "CALENDAR");
	}

	private D data;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4812618721967814696L;

	@SuppressWarnings("unchecked")
	@Override
	protected void init() {
		setData((D)getInitF().apply(getValue()));
	}

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}

	@Override
	protected void init(D value) {
		setData((D)getInitF().apply(value));
		
	}
}
