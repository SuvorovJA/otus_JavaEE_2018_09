package ru.otus.sua.L11;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/montlyPayment")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class MonthlyPaymentServiceV1 {

    @GET
    @Path("{periods}/{loanAmount}/{interestRates}")
    public Response estimatePayments(@PathParam("periods") int periods,
                                     @PathParam("loanAmount") double loanAmount,
                                     @PathParam("interestRates") double interestRates) {
        CalcStrategy calcStrategy = new DiffCalcStrategy() {};
        return Response.ok(calcStrategy.calculator(periods, loanAmount, interestRates)).build();
    }

    @POST
    public Response estimatePaymentsForm(@FormParam("periods") int periods,
                                         @FormParam("loanAmount") double loanAmount,
                                         @FormParam("interestRates") double interestRates) {
        CalcStrategy calcStrategy = new DiffCalcStrategy() {};
        return Response.ok(calcStrategy.calculator(periods, loanAmount, interestRates)).build();
    }


}
