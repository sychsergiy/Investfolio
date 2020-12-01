package investfolio.fetchers;

import investfolio.Rate;

public interface AssetRateFetcher {
    Rate fetch() throws FailedToFetchAssetRateException;
}
