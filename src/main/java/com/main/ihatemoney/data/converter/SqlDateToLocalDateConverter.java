package com.main.ihatemoney.data.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.sql.Date;
import java.time.LocalDate;

public class SqlDateToLocalDateConverter implements Converter<LocalDate, Date> {

    @Override
    public Result<Date> convertToModel(LocalDate localDate, ValueContext valueContext) {
        if (localDate == null) {
            return Result.ok(null);
        }
        return Result.ok(Date.valueOf(localDate));
    }

    @Override
    public LocalDate convertToPresentation(Date date, ValueContext valueContext) {
        if (date == null) {
            return LocalDate.now();
        }
        return date.toLocalDate();
    }
}
