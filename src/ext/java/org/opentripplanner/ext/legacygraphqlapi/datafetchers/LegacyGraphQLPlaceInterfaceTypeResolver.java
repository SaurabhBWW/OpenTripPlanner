package org.opentripplanner.ext.legacygraphqlapi.datafetchers;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;
import org.opentripplanner.model.Stop;
import org.opentripplanner.routing.StopFinder;
import org.opentripplanner.routing.bike_park.BikePark;
import org.opentripplanner.routing.bike_rental.BikeRentalStation;

public class LegacyGraphQLPlaceInterfaceTypeResolver implements TypeResolver {

  @Override
  public GraphQLObjectType getType(TypeResolutionEnvironment environment) {
    Object o = environment.getObject();
    GraphQLSchema schema = environment.getSchema();

    if (o instanceof BikePark) return schema.getObjectType("BikePark");
    if (o instanceof BikeRentalStation) return schema.getObjectType("BikeRentalStation");
    // if (o instanceof CarPark) return schema.getObjectType("CarPark");
    if (o instanceof StopFinder.DepartureRow) return schema.getObjectType("DepartureRow");
    if (o instanceof Stop) return schema.getObjectType("Stop");

    return null;
  }
}