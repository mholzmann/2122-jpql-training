package at.htl;

import at.htl.model.Penalty;
import at.htl.model.Player;
import at.htl.results.GenderCount;
import at.htl.results.MinMaxAmount;
import at.htl.results.PlayerPenalties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class Repository {

    @Inject
    private EntityManager em;

    public List<Player> getAllPlayers() {
        return Collections.emptyList();
    }

    /**
     * Returns players living in a specified town
     *
     * @param town name of the town
     */
    public List<Player> getPlayersLivingInTown(String town) {
        return Collections.emptyList();
    }

    /**
     * Returns players of a certain gender born before a specified year
     *
     * @param female         male or female
     * @param bornBeforeYear the exclusive year before someone has to be born
     */
    public List<Player> getPlayersByGenderAndAge(boolean female, int bornBeforeYear) {
        return Collections.emptyList();
    }

    /**
     * Returns penalties issued between two dates
     *
     * @param start the first (earlier) date, inclusive
     * @param end   the second (later) date, inclusive
     */
    public List<Penalty> getPenaltiesInDateRange(LocalDate start, LocalDate end) {
        return Collections.emptyList();
    }

    /**
     * Returns penalties with an amount higher or equal to the specified amount
     */
    public List<Penalty> getPenaltiesWithAmountHigherEqualThan(BigDecimal amount) {
        return Collections.emptyList();
    }

    /**
     * Returns the average penalty sum calculated over all penalties
     */
    public Double getAveragePenaltyAmount() {
        return null;
    }

    /**
     * Returns the min & max penalty amount
     */
    public MinMaxAmount getMinMaxPenaltyAmount() {
        return null;
    }

    /**
     * Returns all player numbers who have received a penalty so far
     *
     */
    public List<Long> getPlayerNosWithPenalty() {
        return Collections.emptyList();
    }

    /**
     * Returns all players who have received a penalty so far
     *
     */
    public List<Player> getPlayersWithPenalty() {
        return Collections.emptyList();
    }

    /**
     * Returns all players who either have or have not received a penalty so far
     *
     * @param hasPenalty flag indicating if we want to look for players with or without penalties
     */
    public List<Player> getPlayersWithPenalty(boolean hasPenalty) {
        return Collections.emptyList();
    }

    /**
     * Returns the names of those towns who have at least as many players as specified
     *
     * @param minNoOfPlayers the min. number of players a town has to have
     */
    public List<String> getTownsWithPlayerNumber(Long minNoOfPlayers) {
        return Collections.emptyList();
    }

    /**
     * Returns the number of players for each gender
     */
    public List<GenderCount> getPlayerCountsByGender() {
        return Collections.emptyList();
    }

    /**
     * Returns the penalty sum for all players, including those who never received a penalty (sum = 0)
     */
    public List<PlayerPenalties> getPenaltiesForAllPlayers() {
//        @SuppressWarnings("JpaQlInspection") // IntelliJ thinks coalesce returns an int while it's actually a BigDecimal
        return Collections.emptyList();
    }


}
