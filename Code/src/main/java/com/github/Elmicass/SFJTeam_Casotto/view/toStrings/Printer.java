package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

public interface Printer<T extends Object> {

    String shortToStringVersion(T object);

    String fullToStringVersion(T object);

    void printListOfObjects(List<T> list);

    void printShortVersion(T object);

    void printFullVersion(T object);
    
}
