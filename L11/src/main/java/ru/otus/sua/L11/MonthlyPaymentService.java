package ru.otus.sua.L11;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L11.creditService.AnnCalcStrategy;
import ru.otus.sua.L11.creditService.CalcStrategy;
import ru.otus.sua.L11.creditService.DiffCalcStrategy;
import ru.otus.sua.L11.entity.Estimates;
import ru.otus.sua.L11.entity.ValuePack;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@SwaggerDefinition(
        tags = {
                @Tag(name = "Credit Monthly Payments", description = "RESTful API to estimate Credit Monthly Differential and Annuity Payments.")
        })
@Api(tags = "Credit Monthly Payments")
@Slf4j
@Path("/monthlyPayment")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class MonthlyPaymentService {

    @GET
    @ApiOperation("Calculate diffs payments by Path params and Get method")
    @Path("{version}/{periods}/{loanAmount}/{interestRates}")
    public Response estimatePayments(@PathParam("version")
                                     @ApiParam(name = "version", allowableValues = "v1,v2", value = "v1=diffs, v2=annuity")
                                             String version,
                                     @PathParam("periods") int periods,
                                     @PathParam("loanAmount") double loanAmount,
                                     @PathParam("interestRates") double interestRates,
                                     @Context UriInfo uriInfo) {
        Estimates estimates = getCalcStrategy(version).calculator(periods, loanAmount, interestRates);
        log.info("PATH({}): {}", uriInfo.getAbsolutePath(), estimates.toString());
        return Response.ok(estimates).build();
    }

    @POST
    @ApiOperation("Calculate diffs payments by Object params and Post method")
    public Response estimatePaymentsForm(ValuePack valuePack,
                                         @Context UriInfo uriInfo) {
        if (valuePack == null) {
            log.info("POST({}): {}", uriInfo.getAbsolutePath(), "null request");
            throw new IllegalArgumentException("null param packet object");
        }
        Estimates estimates = getCalcStrategy(valuePack.getVersion())
                .calculator(valuePack.getNumMonths(), valuePack.getLoanAmount(), valuePack.getInterestRate());
        log.info("POST({}): {}", uriInfo.getAbsolutePath(), estimates.toString());
        return Response.ok(estimates).build();
    }

    private CalcStrategy getCalcStrategy(String version) {
        CalcStrategy calcStrategy;
        switch (version) {
            case "v1":
                calcStrategy = new DiffCalcStrategy() {
                };
                break;
            case "v2":
                calcStrategy = new AnnCalcStrategy() {
                };
                break;
            default:
                throw new IllegalArgumentException("unknown algorithm version");
        }
        return calcStrategy;
    }
}
