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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class Repository {

    @Inject
    private EntityManager em;

    public List<Player> getAllPlayers() {
        TypedQuery<Player> query = em.createQuery(
                "select p from Player p ",
                Player.class);
        return query.getResultList();
    }

    /**
     * Returns players living in a specified town
     *
     * @param town name of the town
     */
    public List<Player> getPlayersLivingInTown(String town) {
        TypedQuery<Player> query = em.createQuery("" +
                        " select p from Player p " +
                        " where p.town = :town "
                , Player.class);
        query.setParameter("town", town);
        return query.getResultList();
    }

    /**
     * Returns players of a certain gender born before a specified year
     *
     * @param female         male or female
     * @param bornBeforeYear the exclusive year before someone has to be born
     */
    public List<Player> getPlayersByGenderAndAge(boolean female, int bornBeforeYear) {
        TypedQuery<Player> query = em.createQuery("" +
                        " select p from Player p " +
                        " where p.sex = :sex and p.yearOfBirth < :year ",
                Player.class);
        query.setParameter("sex", female ? 'F' : 'M');
        query.setParameter("year", bornBeforeYear);
        return query.getResultList();
    }

    /**
     * Returns penalties issued between two dates
     *
     * @param start the first (earlier) date, inclusive
     * @param end   the second (later) date, inclusive
     */
    public List<Penalty> getPenaltiesInDateRange(LocalDate start, LocalDate end) {
        TypedQuery<Penalty> query = em.createQuery("" +
                        " select p from Penalty p " +
                        " where p.penDate >= :start and p.penDate <= :end ",
                Penalty.class);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

    /**
     * Returns penalties with an amount higher or equal to the specified amount
     */
    public List<Penalty> getPenaltiesWithAmountHigherEqualThan(BigDecimal amount) {
        TypedQuery<Penalty> query = em.createQuery("" +
                        " select p from Penalty p " +
                        " where p.amount >= :amount ",
                Penalty.class);
        query.setParameter("amount", amount);
        return query.getResultList();
    }

    /**
     * Returns the average penalty sum calculated over all penalties
     */
    public Double getAveragePenaltyAmount() {
        TypedQuery<Double> query = em.createQuery("" +
                        " select avg(p.amount) from Penalty p ",
                Double.class);
        return query.getSingleResult();
    }

    /**
     * Returns the min & max penalty amount
     */
    public MinMaxAmount getMinMaxPenaltyAmount() {
        TypedQuery<MinMaxAmount> query = em.createQuery("" +
                        " select new at.htl.results.MinMaxAmount(min(p.amount), max(p.amount)) " +
                        " from Penalty p ",
                MinMaxAmount.class);
        return query.getSingleResult();
    }

    /**
     * Returns all player numbers who have received a penalty so far
     *
     */
    public List<Long> getPlayerNosWithPenalty() {
        TypedQuery<Long> query = em.createQuery("" +
                        " select pl.playerNo from Penalty pen " +
                        " join pen.player pl " +
                        " group by pl.playerNo ",
                Long.class);
        return query.getResultList();
    }

    /**
     * Returns all players who have received a penalty so far
     *
     */
    public List<Player> getPlayersWithPenalty() {
        TypedQuery<Player> query = em.createQuery("" +
                        " select pl from Player pl " +
                        " where pl in ( " +
                        "   select pen.player from Penalty pen" +
                        ") ",
                Player.class);

//        TypedQuery<Player> query = em.createQuery("" +
//                        " select p from Player p " +
//                        " where p.penalties.size > 0",
//                Player.class);
        return query.getResultList();
    }

    /**
     * Returns all players who either have or have not received a penalty so far
     *
     * @param hasPenalty flag indicating if we want to look for players with or without penalties
     */
    public List<Player> getPlayersWithPenalty(boolean hasPenalty) {
        TypedQuery<Player> query = em.createQuery("" +
                        " select p from Player p " +
                        " where (:hasPenalty = true and p.penalties.size > 0) or" +
                        "       (:hasPenalty = false and p.penalties.size = 0)",
                Player.class);
        query.setParameter("hasPenalty", hasPenalty);
        return query.getResultList();
    }

    /**
     * Returns the names of those towns who have at least as many players as specified
     *
     * @param minNoOfPlayers the min. number of players a town has to have
     */
    public List<String> getTownsWithPlayerNumber(Long minNoOfPlayers) {
        TypedQuery<String> query = em.createQuery("" +
                        " select p.town from Player p " +
                        " group by p.town " +
                        " having count(p) >= :minNoOfPlayers",
                String.class);
        query.setParameter("minNoOfPlayers", minNoOfPlayers);
        return query.getResultList();
    }

    /**
     * Returns the number of players for each gender
     */
    public List<GenderCount> getPlayerCountsByGender() {
        TypedQuery<GenderCount> query = em.createQuery("" +
                        " select new at.htl.results.GenderCount(pl.sex, count(pl)) " +
                        " from Player pl " +
                        " group by pl.sex",
                GenderCount.class);
        return query.getResultList();
    }

    /**
     * Returns the penalty sum for all players, including those who never received a penalty (sum = 0)
     */
    public List<PlayerPenalties> getPenaltiesForAllPlayers() {
        @SuppressWarnings("JpaQlInspection") // IntelliJ thinks coalesce returns an int while it's actually a BigDecimal
        TypedQuery<PlayerPenalties> query = em.createQuery("" +
                        " select new at.htl.results.PlayerPenalties(pl, sum(coalesce(pen.amount, 0.0))) " +
                        " from Player pl " +
                        " left join Penalty pen on pl = pen.player" +
                        " group by pl",
                PlayerPenalties.class);
        return query.getResultList();
    }


}
