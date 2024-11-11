package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.UsersRepo;
import com.prs.model.UserLogin;
import com.prs.model.Users;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {
	@Autowired
	private UsersRepo usersRepo;
	
	@GetMapping ("/")
	public List<Users> getAllUsers()
	{
		return usersRepo.findAll();
	}
	
	@GetMapping ("/{id}")
	public Optional<Users> getUserById(@PathVariable int id)
	{
		Optional<Users> u = usersRepo.findById(id);
		if (u.isPresent())
		{return u;}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id "+id);}
	}
	
	@PostMapping ("")
	public Users addUser (@RequestBody Users user)
	{
		return usersRepo.save(user);
	}
	
	@PutMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putUser(@PathVariable int id, @RequestBody Users user)
	{
		if (id != user.getId())
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID mismatch vs URL.");}
		else if (usersRepo.existsById(user.getId()))
			{usersRepo.save(user);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id "+id);}
	}
	

	@DeleteMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser (@PathVariable int id)
	{
		if (usersRepo.existsById(id))
		{usersRepo.deleteById(id);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id "+id);}
	}
	
	//logging in users
	@PostMapping ("/login")
	public Optional<Users> login(@RequestBody UserLogin userLogin)
	{
		Optional<Users> user = usersRepo.findByUsernameAndPassword (userLogin.getUsername(), userLogin.getPassword());
		
		if (user.isPresent())
		{return user;}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for name "+userLogin.getUsername());}
	}

}
