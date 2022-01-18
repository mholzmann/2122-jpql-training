package at.htl;

import at.htl.model.Penalty;
import at.htl.model.Player;
import at.htl.results.GenderCount;
import at.htl.results.MinMaxAmount;
import at.htl.results.PlayerPenalties;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Path("/api")
public class Resource {

    @Inject
    Repository repo;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/allPlayers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> getAllPlayers() {
        return repo.getAllPlayers();
    }

    @GET
    @Path("/playersFromTown/{town}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> getPlayersLivingInTown(@PathParam("town") String town) {
        return repo.getPlayersLivingInTown(town);
    }

    @GET
    @Path("/playersByGenderAndAge")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayersByGenderAndAge(@QueryParam("female") Boolean female, @QueryParam("bornBeforeYear") Integer bornBeforeYear) {
        if (female == null || bornBeforeYear == null) {
            return Response.status(BAD_REQUEST).entity("at least on query param missing").build();
        }
        List<Player> playerList = repo.getPlayersByGenderAndAge(female, bornBeforeYear);
        return Response.ok().entity(playerList).build();
    }

    @GET
    @Path("/penaltiesInDateRange")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPenaltiesInDateRange(@QueryParam("start") String start, @QueryParam("end") String end) {
        if (start == null || end == null) {
            return Response.status(BAD_REQUEST).entity("at least on query param missing").build();
        }
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<Penalty> penaltyList = repo.getPenaltiesInDateRange(startDate, endDate);
        return Response.ok().entity(penaltyList).build();
    }

    @GET
    @Path("/penaltiesWithAmountHigherEqualThan/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Penalty> getPenaltiesWithAmountHigherEqualThan(@PathParam("amount") BigDecimal amount) {
        return repo.getPenaltiesWithAmountHigherEqualThan(amount);
    }

    @GET
    @Path("/averagePenaltyAmount/")
    @Produces(MediaType.APPLICATION_JSON)
    public Double getAveragePenaltyAmount() {
        return repo.getAveragePenaltyAmount();
    }

    @GET
    @Path("/minMaxPenaltyAmount/")
    @Produces(MediaType.APPLICATION_JSON)
    public MinMaxAmount getMinMaxPenaltyAmount() {
        return repo.getMinMaxPenaltyAmount();
    }

    @GET
    @Path("/playerNosWithPenalty/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> getPlayerNosWithPenalty() {
        return repo.getPlayerNosWithPenalty();
    }

    @GET
    @Path("/playersWithPenalty/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> getPlayersWithPenalty() {
        return repo.getPlayersWithPenalty();
    }

    @GET
    @Path("/playersWithPenalty/{hasPenalty}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> getPlayersWithPenalty(@PathParam("hasPenalty") boolean hasPenalty) {
        return repo.getPlayersWithPenalty(hasPenalty);
    }

    @GET
    @Path("/townsWithPlayerNumber/{minNoOfPlayers}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTownsWithPlayerNumber(@PathParam("minNoOfPlayers") long minNoOfPlayers) {
        return repo.getTownsWithPlayerNumber(minNoOfPlayers);
    }

    @GET
    @Path("/playerCountsByGender")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GenderCount> getPlayerCountsByGender() {
        return repo.getPlayerCountsByGender();
    }

    @GET
    @Path("/penaltiesForAllPlayers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayerPenalties> getPenaltiesForAllPlayers() {
        return repo.getPenaltiesForAllPlayers();
    }

}