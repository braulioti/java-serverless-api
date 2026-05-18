package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class Handler implements RequestHandler<Object, APIGatewayProxyResponseEvent> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(Object input, Context context) {

        context.getLogger().log("Starting Lambda execution");

        try {

            List<Map<String, Object>> tasks = List.of(
                    Map.of(
                            "id", 1,
                            "title", "Study AWS"
                    ),
                    Map.of(
                            "id", 2,
                            "title", "Learn CloudFormation"
                    )
            );

            String responseBody = mapper.writeValueAsString(tasks);

            context.getLogger().log("Response generated successfully");

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(Map.of(
                            "Content-Type", "application/json"
                    ))
                    .withBody(responseBody);

        } catch (Exception exception) {

            context.getLogger().log(
                    "Error during execution: " + exception.getMessage()
            );

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("Internal server error");
        }
    }
}