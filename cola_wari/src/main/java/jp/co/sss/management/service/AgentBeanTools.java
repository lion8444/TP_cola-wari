package jp.co.sss.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.AgentBean;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.repository.AgentRepository;

@Service
public class AgentBeanTools {
	
	@Autowired
	AgentRepository agentRepository;
	
	public List<AgentBean> copyEntityListToBeanList(List<Agent> entityList) {
		
		List<AgentBean> beanList = new ArrayList<AgentBean>();

		for (Agent entity : entityList) {
			AgentBean bean = new AgentBean();
			BeanUtils.copyProperties(entity, bean);

			
			beanList.add(bean);
		}

		return beanList;
	}

}
