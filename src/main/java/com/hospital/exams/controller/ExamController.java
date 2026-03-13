package com.hospital.exams.controller;

import com.hospital.exams.dto.ExamRequest;
import com.hospital.exams.enums.ExamStatus;
import com.hospital.exams.model.Exam;
import com.hospital.exams.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
public class ExamController {

	private final ExamService examService;

	public ExamController(ExamService examService) {
		this.examService = examService;
	}

	@GetMapping
	public List<Exam> getAll() {
		return this.examService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Exam> getById(@PathVariable Long id) {
		return ResponseEntity.ok(this.examService.getById(id));
	}

	@PostMapping
	public ResponseEntity<Exam> create(@RequestBody ExamRequest examRequest) {
		return ResponseEntity.ok(this.examService.create(examRequest));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Exam> changeStatus(@PathVariable Long id, @RequestParam(defaultValue = "SCHEDULED") ExamStatus status) {
		return ResponseEntity.ok(this.examService.changeStatus(id, status));
	}

}
