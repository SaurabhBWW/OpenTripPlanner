## Basic OTP Architecture

At the core of OpenTripPlanner is a library of Java code that finds efficient paths through multi-modal transportation networks built from [OpenStreetMap](http://wiki.openstreetmap.org/wiki/Main_Page) and [GTFS](https://developers.google.com/transit/gtfs/) data. Several different services are built upon this library:

The **OTP Routing API** is a [RESTful](https://en.wikipedia.org/wiki/Representational_state_transfer) web service that responds to journey planning requests with itineraries in a JSON or XML representation. You can combine this API with OTP's standard Javascript front end to provide users with trip planning functionality in a familiar map interface, or write your own applications that talk directly to the API.

The **OTP Transit Index API** is another [RESTful](https://en.wikipedia.org/wiki/Representational_state_transfer) web service that provides information derived from the input GTFS feed(s). Examples include routes serving a particular stop, upcoming vehicles at a particular stop, upcoming stops on a given trip, etc. More complex transit data requests can be formulated using a GraphQL API.
