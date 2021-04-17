package com.coursesystem.app.services;

import java.util.List;
import java.util.Optional;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Agent;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.AgentForm;
import com.coursesystem.app.repository.AgentRepository;
import com.coursesystem.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentsServiceImpl implements AgentService {
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AgentRepository agentRepo;

    public List<Agent> findAll() {
        return agentRepo.findAll();
    }

    public Agent save(Agent agent) {
        this.agentRepo.save(agent);
        return agent;
    }

    public void delete(Agent agent) {
        this.agentRepo.delete(agent);
    }

    @Override
    public Agent findById(Long id) throws nonExistentIdException {
        Optional<Agent> optionalAgent = agentRepo.findById(id);
        if (Optional.empty().equals(optionalAgent)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }
        Agent agent = optionalAgent.get();
        return agent;
    }

    @Override
    public Agent chargeFormData(AgentForm agentForm, Agent agent) throws nonExistentIdException {
        Optional<User> optionalUser = userRepo.findById(agentForm.getId());
        if (Optional.empty().equals(optionalUser)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }
        User user = optionalUser.get();
        agent.setUser(user);
        agent.setName(agentForm.getName());
        agent.setLastname(agentForm.getLastname());
        agent.setDocumentType(agentForm.getDocumentType());
        agent.setDocumentNumber(agentForm.getDocumentNumber());
        agent.setJob(agentForm.getJob());
        return agent;
    }
}
