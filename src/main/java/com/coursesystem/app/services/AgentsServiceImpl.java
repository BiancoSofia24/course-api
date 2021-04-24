package com.coursesystem.app.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Agent;
import com.coursesystem.app.models.Role;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.AgentForm;
import com.coursesystem.app.repository.AgentRepository;
import com.coursesystem.app.repository.RoleRepository;
import com.coursesystem.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentsServiceImpl implements AgentService {
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AgentRepository agentRepo;

    @Autowired
    private RoleRepository roleRepo;

    public List<Agent> findAll() {
        return this.agentRepo.findAll();
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
    public Agent chargeFormData(AgentForm agentForm) throws nonExistentIdException {
        Optional<User> optionalUser = userRepo.findById(agentForm.getUserId());

        if (Optional.empty().equals(optionalUser)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }
        
        Agent agent = new Agent();
        User user = optionalUser.get();
        agent.setUser(user);
        agent.setName(agentForm.getName());
        agent.setLastname(agentForm.getLastname());
        agent.setDocumentType(agentForm.getDocumentType());
        agent.setDocumentNumber(agentForm.getDocumentNumber());
        agent.setJob(agentForm.getJob());

        Optional<Role> role = roleRepo.findById(2L);
        role.get().getUserRole();
        Set<Role> set = new HashSet<Role>();
        set.add(role.get());
        user.setRole(set);
        
        return agent;
    }

    @Override
    public Agent update(AgentForm agentForm, Long id) throws nonExistentIdException {
        Optional<Agent> optionalAgent = agentRepo.findById(id);
        
        if (Optional.empty().equals(optionalAgent)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Agent agent = optionalAgent.get();
        agent.setId(agentForm.getUserId());
        agent.setName(agentForm.getName());
        agent.setLastname(agentForm.getLastname());
        agent.setDocumentType(agentForm.getDocumentType());
        agent.setDocumentNumber(agentForm.getDocumentNumber());
        agent.setJob(agentForm.getJob());

        return agent;
    }
}
