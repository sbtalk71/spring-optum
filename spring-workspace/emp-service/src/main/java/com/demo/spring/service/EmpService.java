package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.EmpRepository;
import com.demo.spring.entity.Emp;

@RestController
public class EmpService {
	
	@Autowired
	private EmpRepository repo;

	//http://localhost:8181/emp/104
	@RequestMapping(path="/emp/find/{id}",method=RequestMethod.GET,produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity findEmp(@PathVariable("id")int id) {
		Optional<Emp> o=repo.findById(id);
			
		if(o.isPresent()) {
			
			return ResponseEntity.ok(o.get());
		}else {
			return ResponseEntity.ok("Emp Not Found...");
		}
	}
	
	@RequestMapping(path="/emp/location",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Emp>> getAllFromCity(@RequestParam("city")String cityName){
		return ResponseEntity.ok(repo.getEmpFromHyd(cityName));
	}
	
	@RequestMapping(path="/emp/save",method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> enrollEmp(@RequestBody Emp e){
		if(repo.existsById(e.getEmpId())) {
			return ResponseEntity.ok("{\"status\":\"Employee already exists...\"}");
		}else {
			repo.save(e);
			return ResponseEntity.ok("{\"status\":\"Employee Saved Successfully...\"}");
		}
	}
	
	@RequestMapping(path="/emp/delete",method=RequestMethod.DELETE,
					produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEmp(@RequestParam("id")int id){
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return ResponseEntity.ok("{\"status\":\"Employee Deleted...\"}");
		}else {
			
			return ResponseEntity.ok("{\"status\":\"Employee Not Found...\"}");
		}
	}
	
	@PutMapping(path="/emp/update",consumes="application/json",produces="text/plain")
	
	//@RequestMapping(path="/emp/update",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.TEXT_PLAIN_VALUE)
	
	public ResponseEntity updateEmp(@RequestBody Emp e) {
		repo.save(e);
		return ResponseEntity.ok("Emp Updated...");
	}
	
	@RequestMapping(path="/emp",method=RequestMethod.GET,produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Emp>> getAll(){
		return ResponseEntity.ok(repo.findAll());
	}
	
}
