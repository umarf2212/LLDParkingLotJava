package strategies.feescalculation;

import models.Ticket;

public interface FeesCalculationStrategy {
    int calculateFees(Ticket ticket);
}
