package com.cts.ticketbookingservice.dto;

import java.util.List;

import com.cts.ticketbookingservice.model.TicketBooking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TicketsDTO {
	private List<TicketBooking> tickets;
	
}
