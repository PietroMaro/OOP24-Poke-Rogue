package it.unibo.PokeRogue.utilities;

import java.util.function.BiFunction;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
public class RangeImpl<T extends Number> implements Range<T>{

	@Getter @Setter
    private T currentMin;
	@Getter @Setter
    private T currentMax;
	@Getter 
    private T currentValue;



    public RangeImpl(T currentMin,T currentMax,T currentValue){

        this.currentMin = currentMin;
        
        this.currentMax = currentMax;
        
        this.currentValue = currentValue;
    }

    @Override
    public void increment(T x) {
        double newValue = this.currentValue.doubleValue() + x.doubleValue();
        if (newValue <= this.currentMax.doubleValue()) {
            this.currentValue = convertToType(newValue);
        } else {
            this.currentValue = currentMax;
        }
    }

    @Override
    public void decrement(T x) {
        double newValue = this.currentValue.doubleValue() - x.doubleValue();
        if (newValue > this.currentMin.doubleValue()) {
            this.currentValue = convertToType(newValue);
        } else {
            this.currentValue = currentMin;
        }
    }

	private T convertToType(double value) {
        if (currentValue instanceof Integer) {
            return (T) Integer.valueOf((int) value);
        } else if (currentValue instanceof Double) {
            return (T) Double.valueOf(value);
        } else if (currentValue instanceof Long) {
            return (T) Long.valueOf((long) value);
        } else if (currentValue instanceof Float) {
            return (T) Float.valueOf((float) value);
        }
        throw new UnsupportedOperationException("Unsupported Number type");
    }

	@Override
	public void setCurrentValue(T newValue){
		this.currentValue = newValue;
		if(newValue.doubleValue()> this.currentMax.doubleValue()){
			this.currentValue = currentMax;
		}
		if(newValue.doubleValue() < this.currentMin.doubleValue()){
			this.currentValue = currentMin;
		}
	}
}
