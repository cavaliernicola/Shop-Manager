package org.lookbook.utils;
import com.opencsv.bean.customconverter.ConverterLanguageToBoolean;

public class ConvertItalianToBoolean<T, I> extends ConverterLanguageToBoolean<T, I> {
    private static final String SI = "si";
    private static final String NO = "no";
    private static final String[] TRUE_STRINGS = {SI, "vero", "1"};
    private static final String[] FALSE_STRINGS = {NO, "falso", "0"};

    public ConvertItalianToBoolean() {}

    @Override
    protected String getLocalizedTrue() {
        return SI;
    }

    @Override
    protected String getLocalizedFalse() {
        return NO;
    }

    @Override
    protected String[] getAllLocalizedTrueValues() {
        return TRUE_STRINGS;
    }

    @Override
    protected String[] getAllLocalizedFalseValues() {
        return FALSE_STRINGS;
    }
}