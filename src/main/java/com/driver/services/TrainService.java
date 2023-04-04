package com.driver.services;

import com.driver.EntryDto.AddTrainEntryDto;
import com.driver.EntryDto.SeatAvailabilityEntryDto;
import com.driver.model.Passenger;
import com.driver.model.Station;
import com.driver.model.Ticket;
import com.driver.model.Train;
import com.driver.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {

    @Autowired
    TrainRepository trainRepository;

    public Integer addTrain(AddTrainEntryDto trainEntryDto){

        //Add the train to the trainRepository
        //and route String logic to be taken from the Problem statement.
        //Save the train and return the trainId that is generated from the database.
        //Avoid using the lombok library

        //creating trainroute from list
        String route = "";
        int n =  trainEntryDto.getStationRoute().size();
        for(int i = 0 ; i < n;i++){

            if(i == n-1){
                route += trainEntryDto.getStationRoute().get(i);
            }else{
                route += trainEntryDto.getStationRoute().get(i) + ",";
            }

        }

        //creating train object
        Train train = new Train();
        train.setRoute(route);
        train.setDepartureTime(trainEntryDto.getDepartureTime());
        train.setNoOfSeats(trainEntryDto.getNoOfSeats());

        Train savedTrain = trainRepository.save(train);



        return savedTrain.getTrainId();
    }

    public Integer calculateAvailableSeats(SeatAvailabilityEntryDto seatAvailabilityEntryDto){

        //Calculate the total seats available
        //Suppose the route is A B C D
        //And there are 2 seats avaialble in total in the train
        //and 2 tickets are booked from A to C and B to D.
        //The seat is available only between A to C and A to B. If a seat is empty between 2 station it will be counted to our final ans
        //even if that seat is booked post the destStation or before the boardingStation
        //Inshort : a train has totalNo of seats and there are tickets from and to different locations
        //We need to find out the available seats between the given 2 stations.

//        Train train = trainRepository.findById(seatAvailabilityEntryDto.getTrainId()).get();
//        int availableSeats = train.getNoOfSeats();
//
//        //available seats till boarding station
//        String stations[] = train.getRoute().split(",");




       return 0;
    }

    public Integer calculatePeopleBoardingAtAStation(Integer trainId,Station station) throws Exception{

        //We need to find out the number of people who will be boarding a train from a particular station
        //if the trainId is not passing through that station
        //throw new Exception("Train is not passing from this station");
        //  in a happy case we need to find out the number of such people.
         Train train = trainRepository.findById(trainId).get();;

         String[] stations = train.getRoute().split(",");
         boolean passes = false;
         for(int i = 0 ; i < stations.length;i++){

             if(station.toString().compareTo(stations[i].toString()) == 0){
                 passes = true;
                 break;
             }

         }

         if(!passes){
             throw new Exception("Train is not passing from this station");
         }

         int passengersBoarded = 0;

         for(Ticket ticket : train.getBookedTickets()){

             if(ticket.getFromStation().compareTo(station) == 0){
                 passengersBoarded += ticket.getPassengersList().size();
             }

         }



        return passengersBoarded;
    }
//
    public Integer calculateOldestPersonTravelling(Integer trainId){

        //Throughout the journey of the train between any 2 stations
        //We need to find out the age of the oldest person that is travelling the train
        //If there are no people travelling in that train you can return 0

        Train train;
        try{
            train = trainRepository.findById(trainId).get();
        }catch (Exception e){
            return 0;
        }

        int age = 0;

       for(Ticket ticket : train.getBookedTickets()){

           for(Passenger passenger : ticket.getPassengersList()){

               if(passenger.getAge() > age)
                   age = passenger.getAge();

           }

       }

       return age;

    }

    public List<Integer> trainsBetweenAGivenTime(Station station, LocalTime startTime, LocalTime endTime){

        //When you are at a particular station you need to find out the number of trains that will pass through a given station
        //between a particular time frame both start time and end time included.
        //You can assume that the date change doesn't need to be done ie the travel will certainly happen with the same date (More details
        //in problem statement)
        //You can also assume the seconds and milli seconds value will be 0 in a LocalTime format.
        int numberOfTrains = 0;
        for(Train train : trainRepository.findAll()){

            LocalTime time = train.getDepartureTime();
            String[] stations = train.getRoute().split(",");

            for(int i = 0 ; i < stations.length; i++){

                time = time.plusHours(i);
                //this train passes through the given station
                if(stations[i].compareTo(station.toString()) == 0){

                    //calculate whether this train will pass that station in the given time frame or not
                    if(time.equals(startTime) || time.equals(endTime) || (time.isAfter(startTime) && time.isBefore(endTime) )  ){

                        numberOfTrains++;

                    }


                    break;

                }

            }


        }

        List<Integer> trains = new ArrayList<>();
        trains.add(numberOfTrains);
        return trains;
    }

}
