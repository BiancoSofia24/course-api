package com.coursesystem.app.services;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.forms.AgentForm;
import com.coursesystem.app.models.Agent;

public interface AgentService {
    
    public Agent findById(Long id) throws nonExistentIdException;
    
    public Agent chargeFormData(AgentForm agentForm, Agent agent) throws nonExistentIdException;
}
