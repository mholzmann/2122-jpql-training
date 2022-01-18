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
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}