package investfolio.repository;

import investfolio.Rate;

public interface CurrencyRateFetcher {
    Rate fetch(String currency);  // todo: add throws exception
}
