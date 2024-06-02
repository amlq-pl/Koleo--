package pl.tcs.oopproject.view;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TODO: Wlozyc do odpwiedzniejo foldreu

public class SimpleDateProperty implements Property<LocalDate> {
	private final List<ChangeListener<? super LocalDate>> changeListeners = new ArrayList<>();
	private final List<InvalidationListener> invalidationListeners = new ArrayList<>();
	private LocalDate val;
	
	public SimpleDateProperty(LocalDate date) {
		val = date;
	}
	
	public SimpleDateProperty() {
		val = null;
	}
	
	@Override
	public void bind(ObservableValue<? extends LocalDate> observableValue) {
		observableValue.addListener((obs, oldCal, newVal) -> setValue(newVal));
	}
	
	@Override
	public void unbind() {
		throw new UnsupportedOperationException("Subway surfers");
	}
	
	@Override
	public boolean isBound() {
		return false;
	}
	
	@Override
	public void bindBidirectional(Property<LocalDate> property) {
		this.setValue(property.getValue());
		property.setValue(this.getValue());
		this.addListener((obs, oldVal, newVal) -> property.setValue(newVal));
		property.addListener((obs, oldVal, newVal) -> this.setValue(newVal));
	}
	
	@Override
	public void unbindBidirectional(Property property) {
		throw new UnsupportedOperationException("Subway surfers");
	}
	
	@Override
	public Object getBean() {
		return null;
	}
	
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public void addListener(ChangeListener<? super LocalDate> changeListener) {
		changeListeners.add(changeListener);
	}
	
	@Override
	public void removeListener(ChangeListener<? super LocalDate> changeListener) {
		changeListeners.remove(changeListener);
	}
	
	@Override
	public LocalDate getValue() {
		return this.val;
	}
	
	@Override
	public void setValue(LocalDate localDate) {
		LocalDate oldDate = this.val;
		this.val = localDate;
		
		for (ChangeListener<? super LocalDate> listener : changeListeners) {
			listener.changed(this, oldDate, this.val);
		}
		
		for (InvalidationListener listener : invalidationListeners) {
			listener.invalidated(this);
		}
	}
	
	@Override
	public void addListener(InvalidationListener invalidationListener) {
		invalidationListeners.add(invalidationListener);
	}
	
	@Override
	public void removeListener(InvalidationListener invalidationListener) {
		invalidationListeners.remove(invalidationListener);
	}
}
