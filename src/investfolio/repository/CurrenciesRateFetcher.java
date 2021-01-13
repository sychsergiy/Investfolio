package investfolio.repository;

import investfolio.Rate;

import java.util.Map;
import java.util.Set;

public interface CurrenciesRateFetcher {
    Map<String, Rate> fetch(Set<String> currencies);  // todo: add throws exception
}
