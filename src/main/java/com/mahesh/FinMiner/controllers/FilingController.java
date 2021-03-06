package com.mahesh.FinMiner.controllers;

import com.mahesh.FinMiner.models.Filing;
import com.mahesh.FinMiner.services.FilingService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/filings")
public class FilingController {
    @Inject
    private FilingService filingService;

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getFilingFromWhale(@HeaderParam
                                                    ("Access-Control-Allow-Origin") String s) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Filing f : filingService.getFilingsForWhale(3)) {
            builder.add(Json.createObjectBuilder().add("filingDate",
                    f.getFilingDate().toString()));
        }
        return builder.build();
    }
}
