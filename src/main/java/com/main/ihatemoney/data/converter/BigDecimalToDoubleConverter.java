package com.main.ihatemoney.data.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.math.BigDecimal;

public class BigDecimalToDoubleConverter implements Converter<Double, BigDecimal> {

    @Override
    public Result<BigDecimal> convertToModel(Double aDouble, ValueContext valueContext) {
        if (aDouble == null) {
            return Result.ok(null);
        }
        return Result.ok(BigDecimal.valueOf(aDouble));
    }

    @Override
    public Double convertToPresentation(BigDecimal bigDecimal, ValueContext valueContext) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.doubleValue();
    }
}
