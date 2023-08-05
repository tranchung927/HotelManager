package vn.edu.aptech.hotelmanager.repo.converter;

import vn.edu.aptech.hotelmanager.common.converter.IConverter;

import java.sql.ResultSet;

public interface IEntityConverter<D> extends IConverter<ResultSet, D> {
}