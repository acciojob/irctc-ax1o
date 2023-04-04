package com.driver.services;


import com.driver.EntryDto.BookTicketEntryDto;
import com.driver.model.Passenger;
import com.driver.model.Ticket;
import com.driver.model.Train;
import com.driver.repository.PassengerRepository;
import com.driver.repository.TicketRepository;
import com.driver.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    PassengerRepository passengerRepository;


    public Integer bookTicket(BookTicketEntryDto bookTicketEntryDto)throws Exception{

        //Check for validity
        //Use bookedTickets List from the TrainRepository to get bookings done against that train
        // Incase the there are insufficient tickets
        // throw new Exception("Less tickets are available");
        //otherwise book the ticket, calculate the price and other details
        //Save the information in corresponding DB Tables
        //Fare System : Check problem statement
        //Incase the train doesn't pass through the requested stations
        //throw new Exception("Invalid stations");
        //Save the bookedTickets in the train Object
        //Also in the passenger Entity change the attribute bookedTickets by using the attribute bookingPersonId.
       //And the end return the ticketId that has come from db

        Train train = trainRepository.findById(bookTicketEntryDto.getTrainId()).get();

        String stations[] = train.getRoute().split(",");

        //calculate available seats between boarding station to dest station

        final int pricePerStation = 300;

        //chcek if the train passes through the station or not
        int i = 0;

        boolean fromStation = false;
        //first check from station
        for(i = 0 ; i < stations.length;i++){
            if(stations[i].compareTo(bookTicketEntryDto.getFromStation().toString()) == 0){
                fromStation = true;
                break;
            }
        }

        boolean toStation = false;
        //check to station
        for(i = i ; i < stations.length;i++){

            if(stations[i].compareTo(bookTicketEntryDto.getToStation().toString()) == 0){
                toStation = true;
                break;
            }

        }

        if(!fromStation || !toStation){
            throw new Exception("Invalid stations");
        }




       return 1;

    }
}
