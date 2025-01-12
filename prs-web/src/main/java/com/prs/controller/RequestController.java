package com.prs.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.RequestRepo;
import com.prs.model.Request;
import com.prs.model.RequestForm;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
	@Autowired
	private RequestRepo requestRepo;

	@GetMapping("/")
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Request> getRequestById(@PathVariable int id) {
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent()) {
			return r;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@PostMapping("")
	public Request addRequestForm(@RequestBody RequestForm requestForm) {
		Request r = new Request();
		
		List<Request> requests = getAllRequests();
		String maxReq = requests.get(requests.size()-1).getRequestNumber();
		
		r.setId(requestForm.getId());
		r.setUser(requestForm.getUser());
		r.setRequestNumber(RequestForm.RNum()+(RequestForm.incrementNum(maxReq)));
		r.setDescription(requestForm.getDescription());
		r.setJustification(requestForm.getJustification());
		r.setDateNeeded(requestForm.getDateNeeded());
		r.setDeliveryMode(requestForm.getDeliveryMode());
		r.setStatus("New");
		r.setTotal(0.00);
		r.setSubmittedDate(LocalDateTime.now());
		r.setReasonForRejection(null);
		return requestRepo.save(r);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putRequest(@PathVariable int id, @RequestBody Request request) {
		if (id != request.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request ID mismatch vs URL.");
		} else if (requestRepo.existsById(request.getId())) {
			requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRequest(@PathVariable int id) {
		if (requestRepo.existsById(id)) {
			requestRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@PutMapping("/submit-review/{id}")
	public void submitRequest(@PathVariable int id) {
		Request request = requestRepo.getById(id);

		if (id != request.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request ID mismatch vs URL.");
		} else if (requestRepo.existsById(request.getId())) {
			if (request.getTotal() <= 50.00) {
				request.setStatus("APPROVED");
			} else {
				request.setStatus("SUBMITTED");
			}
			requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@GetMapping("/list-review/{userId}")
	public List<Request> findByUserIdNotAndStatus(@PathVariable int userId, String status) {
		status = "SUBMITTED";
		return requestRepo.findByUserIdNotAndStatus(userId, status);
	}

	@PutMapping("/approve/{id}")
	public void approveRequest(@PathVariable int id) {
		Request request = requestRepo.getById(id);

		if (id != request.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request ID mismatch vs URL.");
		} else if (requestRepo.existsById(request.getId())) {
			request.setStatus("APPROVE");

			requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}
	
	@PutMapping("/reject/{id}")
	public void rejectRequest(@PathVariable int id, @RequestBody String reasonForRejection) {
		Request request = requestRepo.getById(id);

		if (id != request.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request ID mismatch vs URL.");
		} else if (requestRepo.existsById(request.getId())) {
			request.setStatus("REJECTED");
			request.setReasonForRejection(reasonForRejection);
			requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

}
