package controllers;

import models.Filing;
import models.Fund;
import models.Holding;
import services.FundService;
import services.HoldingService;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import java.util.HashMap;
import java.util.List;

@Path("/funds")
public class FundController {
    @Inject
    private FundService fundService;
    @Inject
    private HoldingService holdingService;

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getFilingsFromFund() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Fund f : fundService.getAllFunds()) {
            System.out.println("Getting all the funds...");
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

    @Path("/analyze_fund")
    @GET
    @Produces ("application/json")
    public JsonObject analyzeFund(@QueryParam("fundId") int fund) {
        //Get the latest filing
        FundFilter fundFilter = new FundFilter();
        fundFilter.setMaxResults(2);
        fundFilter.setFundId(fund);
        List<Filing> latestFilings = fundService.getFilingsForFund(fundFilter);
        Filing mostRecentFiling = latestFilings.get(0);
        Filing previousFiling = latestFilings.get(1);

        HashMap<String,Holding> latestHoldidngMap = new HashMap<>();
        HashMap<String,Holding> previousHoldidngMap = new HashMap<>();


        //Get all the holdings in that filing
        List<Holding> holdingsFromLatestFiling = holdingService
                .getHoldingsForFiling(mostRecentFiling.getFilingId());
        List<Holding> holdingsFromPreviousFiling = holdingService
                .getHoldingsForFiling(previousFiling.getFilingId());
        for (Holding p : holdingsFromPreviousFiling) {
            previousHoldidngMap.put(p.getCusip(), p);
        }

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder pieBuilder = Json.createArrayBuilder();
        JsonArrayBuilder tableBuilder = Json.createArrayBuilder();

        JsonArrayBuilder pieTitleArray = Json.createArrayBuilder();
        pieTitleArray.add("Stock");
        pieTitleArray.add("Percentage");
        pieBuilder.add(pieTitleArray);

        for (Holding h : holdingsFromLatestFiling) {
            JsonArrayBuilder lclArray = Json.createArrayBuilder();
            lclArray.add(h.getStock());
            lclArray.add(h.getPosition());
            pieBuilder.add(lclArray);

            JsonArrayBuilder tblArray = Json.createArrayBuilder();
            tblArray.add(h.getStock());
            tblArray.add(h.getCusip());
            tblArray.add(h.getNumshares());
            tblArray.add(h.getPosition());
            tableBuilder.add(tblArray);

            latestHoldidngMap.put(h.getCusip(), h);
        }



        objectBuilder.add("TableData", tableBuilder.build());
        objectBuilder.add("PieData", pieBuilder.build());
        return objectBuilder.build();
    }
}
