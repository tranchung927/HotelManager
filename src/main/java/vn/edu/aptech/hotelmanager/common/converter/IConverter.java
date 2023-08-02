package vn.edu.aptech.hotelmanager.common.converter;

import lombok.NonNull;

public interface IConverter<S, D> {
    D convert(@NonNull S source);
}
