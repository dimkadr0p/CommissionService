package com.khachidze.commissionservice.controller;


import com.khachidze.commissionservice.service.CommissionService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/commission")
public class CommissionResource {
    @Inject
    private CommissionService commissionService;

    @GET
    @Produces("application/json")
    public Response getTotalAmount(@QueryParam("amount") BigDecimal amountRequest) {
        try {
            return Response.ok(commissionService.getTotalAmount(amountRequest)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}