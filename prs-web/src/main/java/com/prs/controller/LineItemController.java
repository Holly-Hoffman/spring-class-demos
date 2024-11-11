package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.LineItemRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Request;

@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")
public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;
	
	@Autowired
	private RequestRepo requestRepo;

	@GetMapping("/{id}")
	public Optional<LineItem> getLineItemById(@PathVariable int id) {
		Optional<LineItem> l = lineItemRepo.findById(id);
		if (l.isPresent()) {
			return l;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Line item not found for id " + id);
		}
	}

	@PostMapping("")
	public LineItem addLineItem(@RequestBody LineItem lineItem) {
		Request request = lineItem.getRequest();
		request.setTotal(recalcTotal(request.getId()));
		requestRepo.save(request);
		return lineItemRepo.save(lineItem);
	}

	@PutMapping ("")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putLineItem(@PathVariable int id, @RequestBody LineItem lineItem)
	{
		if (id != lineItem.getId())
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Line item ID mismatch vs URL.");}
		else if (lineItemRepo.existsById(lineItem.getId()))
			{lineItemRepo.save(lineItem);
			Request request = lineItem.getRequest();
			request.setTotal(recalcTotal(request.getId()));
			requestRepo.save(request);
			}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Line item not found for id "+id);}
	}
	

	@DeleteMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLineItem (@PathVariable int id, LineItem lineItem)
	{
		if (lineItemRepo.existsById(id))
		{lineItemRepo.deleteById(id);
		Request request = lineItem.getRequest();
		request.setTotal(recalcTotal(request.getId()));
		requestRepo.save(request);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Line item not found for id "+id);}
	}
	
	@GetMapping ("/lines-for-req/{reqId}")
	public List<LineItem> findAllRequestsById(@PathVariable int reqId)
	{ 
		return lineItemRepo.findAllByRequestId(reqId);
	}

	public double recalcTotal(int reqId) {
		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(reqId);
		double total = 0.00;
		for (LineItem lineItem : lineItems) {
			double cost = lineItem.getProduct().getPrice() * lineItem.getQuantity();
			total = total + cost;
		}
		return total;
	}

}
