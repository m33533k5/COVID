package application.model;

import java.util.Comparator;

public class MyComparator implements Comparator<CountrieObjects>{
    @Override
    public int compare(CountrieObjects co1, CountrieObjects co2) {
        return co1.getName().compareTo(co2.getName());
    }
}
