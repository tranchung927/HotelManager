package vn.edu.aptech.hotelmanager.common.converter;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ListConverter<S, D> implements IConverter<List<S>, List<D>> {

    private final IConverter<S, D> mConverter;

    public ListConverter(IConverter converter) {
        mConverter = converter;
    }

    @Override
    public List<D> convert(@NonNull List<S> source) {
        List<D> result = new ArrayList<>();
        for (S s : source) {
            result.add(mConverter.convert(s));
        }
        return result;
    }
}
