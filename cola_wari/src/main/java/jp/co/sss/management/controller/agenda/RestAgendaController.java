package jp.co.sss.management.controller.agenda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.repository.AgentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestAgendaController {
	
	@Autowired
	AgentRepository agentRepository;
    
    @PostMapping("/agenda/company_agents")
    public List<Agent> finishAgendaCheck(Integer comId) {
        log.debug("RestAgendaController comId Test : {}", comId);
    	List<Agent> results = agentRepository.findByDeleteFlag(0);
        results = results.stream().filter(agent -> agent.getCompany().getComId().equals(comId)).toList();

        for (Agent agent2 : results) {
            log.debug("RestAgendaController AgentList check : {}", agent2.getAgentId());
        }

        return results;
    }
}
