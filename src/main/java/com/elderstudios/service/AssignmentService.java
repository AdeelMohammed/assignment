package com.elderstudios.service;

import com.elderstudios.domain.assignment;
import com.elderstudios.domain.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    protected AssignmentRepository assignmentRepository;

    public List<assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public assignment save(assignment entry) {
        return assignmentRepository.save(entry);
    }

    public void delete(Long id) {
        assignmentRepository.delete(id);
    }

    public assignment findOne(Long id) {
        return assignmentRepository.findOne(id);
    }
}
