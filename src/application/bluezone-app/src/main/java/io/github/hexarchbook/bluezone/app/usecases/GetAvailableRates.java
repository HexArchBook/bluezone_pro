package io.github.hexarchbook.bluezone.app.usecases;

import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.Rate;
import java.util.Set;
import java.util.function.Supplier;

public class GetAvailableRates implements Supplier<Set<Rate>> {

    private final ForStoringData dataRepository;

    public GetAvailableRates(ForStoringData dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public Set<Rate> get() {
        return this.dataRepository.getAllRates();
    }

}
