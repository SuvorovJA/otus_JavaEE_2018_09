package ru.otus.sua.L11;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Slf4j
@Path("/monthlyPayment")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class MonthlyPaymentServiceV2 {

    @GET
    @Path("{periods}/{loanAmount}/{interestRates}")
    public Response estimatePayments(@PathParam("periods") int periods,
                                     @PathParam("loanAmount") double loanAmount,
                                     @PathParam("interestRates") double interestRates,
                                     @Context UriInfo uriInfo) {
        CalcStrategy calcStrategy = new AnnCalcStrategy() {
        };
        Estimates estimates = calcStrategy.calculator(periods, loanAmount, interestRates);
        log.info("V2_PATH({}): {}", uriInfo.getAbsolutePath(), estimates.toString());
        return Response.ok(estimates).build();
    }

    @POST
    public Response estimatePaymentsForm(ValuePack valuePack,
                                         @Context UriInfo uriInfo) {
        CalcStrategy calcStrategy = new AnnCalcStrategy() {};
        if (valuePack == null) {
            log.info("V2_POST({}): {}", uriInfo.getAbsolutePath(), "null request");
            return Response.noContent().build();
        }
        Estimates estimates = calcStrategy.calculator(valuePack.getNumMonths(), valuePack.getLoanAmount(), valuePack.getInterestRate());
        log.info("V2_POST({}): {}", uriInfo.getAbsolutePath(), estimates.toString());
        return Response.ok(estimates).build();
    }


}
