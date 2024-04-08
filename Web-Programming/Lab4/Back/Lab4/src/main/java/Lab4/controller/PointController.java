package Lab4.controller;


import Lab4.authentication.Secured;
import Lab4.entity.Point;
import Lab4.entity.User;
import Lab4.point.*;
import Lab4.repository.PointRepository;
import Lab4.repository.UserRepository;
import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Lab4.point.PointValidationStatus.INVALID_DATA;
import static Lab4.point.PointValidationStatus.OK;

@Path("/main")
public class PointController {

    @EJB
    UserRepository userRepository;
    @EJB
    PointRepository pointRepository;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    public Response addPoint(
            @Valid PointData pointData,
            @HeaderParam("A-Token") String token){
        try {
            String x = pointData.getX();
            String y = pointData.getY();
            String r = pointData.getR();
            double xValue;
            double yValue;
            double rValue;
            try {
                xValue = Double.parseDouble(x);
                yValue = Double.parseDouble(y);
                rValue = Double.parseDouble(r);
            }catch (Exception e){
                throw new InvalidPointDataException(INVALID_DATA);
            }
            PointValidationStatus status = PointValidator.validate(xValue, yValue, rValue);
            if(status != OK){
                throw new InvalidPointDataException(status);
            }
            boolean result = AreaChecker.check(xValue, yValue, rValue);
            List<User> userList = userRepository.getByToken(token);
            User user = userList.get(0);
            Point point = new Point();
            point.setX(xValue); point.setY(yValue); point.setR(rValue);
            point.setResult(result);
            point.setOwner(user);
            pointRepository.save(point);
            PointResponse pointResponse = new PointResponse(point);
            return Response.ok()
                    .entity(new Gson().toJson(pointResponse))
                    .build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Gson().toJson(Collections.singletonMap(
                            "message", e.getMessage()
                    ))).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response getPoints(@HeaderParam("A-Token") String token){
        List<User> userList = userRepository.getByToken(token);
        List<Point> points = pointRepository.getByOwner(userList.get(0));
        List<PointResponse> pointResponses = new ArrayList<>();
        for(Point point:points){
            pointResponses.add(new PointResponse(point));
        }
        return Response.ok().entity(new Gson().toJson(pointResponses)).build();
    }

    @DELETE
    @Secured
    public Response clearPoints(@HeaderParam("A-Token") String token) {
        List<User> userList = userRepository.getByToken(token);
        pointRepository.clearByOwner(userList.get(0));
        return Response.ok().build();
    }
}
