package jp.co.sss.management.controller.agenda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.management.bean.AgentBean;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.service.AgentBeanTools;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestAgendaController {
	
	@Autowired
	AgentRepository agentRepository;

    @Autowired
    AgentBeanTools agentBeanTools;
    
    @PostMapping("/agenda/company_agents")
    public List<AgentBean> finishAgendaCheck(Integer comId) {
        log.debug("RestAgendaController comId Test : {}", comId);
    	List<Agent> agents = agentRepository.findByDeleteFlag(0);
        agents = agents.stream().filter(agent -> agent.getCompany().getComId().equals(comId)).toList();

        for (Agent agent2 : agents) {
            log.debug("RestAgendaController AgentList check : {}", agent2.getAgentId());
        }
        List<AgentBean> results = agentBeanTools.copyEntityListToBeanList(agents);

        return results;
    }
}
