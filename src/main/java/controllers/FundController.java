package controllers;

import models.Filing;
import models.Fund;
import models.Holding;
import services.FundService;
import services.HoldingService;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
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
    public JsonArray analyzeFund(@QueryParam("fundId") int fund) {
        //Get the latest filing
        FundFilter fundFilter = new FundFilter();
        fundFilter.setMaxResults(2);
        fundFilter.setFundId(fund);
        List<Filing> latestFilings = fundService.getFilingsForFund(fundFilter);
        Filing mostRecentFiling = latestFilings.get(0);
        Filing previousFiling = latestFilings.get(1);

        //Get all the holdings in that filing
        List<Holding> holdingsFromLatestFiling = holdingService
                .getHoldingsForFiling(mostRecentFiling.getFilingId());

        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder titleArray = Json.createArrayBuilder();
        titleArray.add("Stock");
        titleArray.add("Percentage");
        builder.add(titleArray);

        for (Holding h : holdingsFromLatestFiling) {
            JsonArrayBuilder lclArray = Json.createArrayBuilder();
            lclArray.add(h.getStock());
            lclArray.add(h.getPosition());
            builder.add(lclArray);
        }

        return builder.build();
        //create a Pie chart based on total market value

        //Get previous filing
        //Compare increases/decreases in position for each holding
        //Sort by delta. (two tables - increases, decreases)
//        String filingInfo = "<p> Latest Filing: " + mostRecentFiling
//                .getFilingDate() + " ID: " + mostRecentFiling.getFilingId() +
//                "<br> Prev Filing: " + previousFiling.getFilingDate() + " ID:" +
//                previousFiling.getFilingId() + "<br></p>";
//        for (int i=0; i < holdingsFromLatestFiling.size(); i++) {
//            filingInfo += "stock: " + holdingsFromLatestFiling.get(i)
//                    .getStock() + " numShares: " + holdingsFromLatestFiling
//                    .get(i).getNumshares() + "<br>";
//        }
//        String html = "<html><body>Analysis:" + fund + "\n" +
//                filingInfo + "</body></html>";
//        latestFilings.clear();
//        return html;
    }
}
