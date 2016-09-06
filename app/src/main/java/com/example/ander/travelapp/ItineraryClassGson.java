package com.example.ander.travelapp;

import java.util.List;

/**
 * Created by anders on 8/29/2016.
 */

public class ItineraryClassGson {

    private List<ItineraryObjectsClass> Itineraries;

//         SOMETHING IS WRONG IN HERE
//         java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 478
//         IN Itinerary = gson.fromJson(response.toString(), ItineraryClassGson.class);


    public void setItineraries(List<ItineraryObjectsClass> itineraries) {Itineraries = itineraries;}
    public List<ItineraryObjectsClass> getItineraries() {return Itineraries;}

    // stepping into itineraries array
        public class ItineraryObjectsClass {

            private BookingDetailsClass BookingDetailsLink;
            private List<PricingObjClass> PricingOptions;
//            private FlightReturnClass InboundLegId;

            public BookingDetailsClass getBookingDetailsLink() {return BookingDetailsLink;}
//            public FlightReturnClass getInboundLegId() {return InboundLegId;}
            public List<PricingObjClass> getPricingOptions() {
                return PricingOptions;
            }

        }

//      Pricing

            public class PricingObjClass {
                public String Price, DeeplinkUrl;

                public String getPrice() {
                    return Price;
                }
                public String getDeeplinkUrl() {
                    return DeeplinkUrl;
                }
            }

//      Booking

            public class BookingDetailsClass {
                public String Uri;

                public String getUri() {
                    return Uri;
                }
                public void setUri(String uri) {Uri = uri;}
            }

//      Return Flight

//            public class FlightReturnClass {
//
//                public FlightReturnClass OriginStation;
//                public FlightDestinationReturn DestinationStation;
//                public String Departure;
//                public String Arrival;
//                public String Duration;
//
//                public FlightReturnClass getOriginStation() {return OriginStation;}
//                public FlightDestinationReturn getDestinationStation() {return DestinationStation;}
//                public String getDeparture() {return Departure;}
//                public String getArrival() {return Arrival;}
//                public String getDuration() {return Duration;}
//            }
//
//                public class FlightDestinationReturn {
//                    public String Name;
//
//                    public String getName() {return Name;}
//                }
//
//                public class FlightOriginStation {
//                    public String Name;
//
//                    public String getName() {return Name;}
//                }
//
////      Outbound Flight
//
//            public class OutboundFlightClass {
//                public FlightOriginOutbound OriginStation;
//                public FlightDestinationOutbound DestinationStation;
//                public String Departure,Arrival,Duration;
//
//                public FlightOriginOutbound getOriginOutbound() {return OriginStation;}
//                public FlightDestinationOutbound getDestinationOutbound() {return DestinationStation;}
//                public String getDeparture() {return Departure;}
//                public String getArrival() {return Arrival;}
//                public String getDuration() {return Duration;}
//            }
//
//                public class FlightOriginOutbound {
//                    public String Name;
//
//                    public String getName() {return Name;}
//                }
//
//                public class FlightDestinationOutbound {
//                    public String Name;
//
//                    public String getName() {return Name;}
//                }
}
