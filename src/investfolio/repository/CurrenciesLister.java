package investfolio.repository;

import java.util.Set;

public interface CurrenciesLister {
    Set<String> listAvailableCurrencies();
}
