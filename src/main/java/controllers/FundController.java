package controllers;

import models.Filing;
import models.Fund;
import services.FundService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;

@Path("/funds")
public class FundController {
    @Inject
    private FundService fundService;

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getFilingsFromFund() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Fund f : fundService.getAllFunds()) {
            builder.add(Json.createObjectBuilder().add("fundId",
                    f.getFundId()));
            builder.add(Json.createObjectBuilder().add("fundName",
                    f.getFundName()));
        }
        return builder.build();
    }

    @Path("/ordered")
    @GET
    @Produces ("application/json")
    public JsonArray getFilingsFromFundByOrder() {
        FundFilter filter = new FundFilter();
        filter.setFundId(3);
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Filing f : fundService.getFilingsForFund(filter)) {
            builder.add(Json.createObjectBuilder().add("filingDate",
                    f.getFilingDate().toString()));
        }
        return builder.build();
    }

    @Path("/filter")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public JsonArray getFilingsFromFundWithFilter(FundFilter filter) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        //System.out.println(filter);
        for (Filing f : fundService.getFilingsForFund(filter)) {
            builder.add(Json.createObjectBuilder().add("filingDate", f
                    .getFilingDate().toString()));
            builder.add(Json.createObjectBuilder().add("filingId",
                    f.getFilingId()));
        }
        return builder.build();
    }


}
