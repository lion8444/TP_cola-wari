package jp.co.sss.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.CompanyBean;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.Company;
import jp.co.sss.management.repository.AgentRepository;


@Service
public class CompanyBeanTools {

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	AgentBeanTools agentBeanTools;

	public List<CompanyBean> copyEntityListToBeanList(List<Company> entityList) {

		List<CompanyBean> beanList = new ArrayList<CompanyBean>();

		for (Company entity : entityList) {
			CompanyBean bean = new CompanyBean();
			BeanUtils.copyProperties(entity, bean);

			if (entity.getComCagetory() != null) {
				bean.setCateName(entity.getComCagetory().getCateName());
			}

			List<Agent> agentList = agentRepository.findByCompany_ComId(entity.getComId());

			if (!(agentList.isEmpty())) {
				bean.setAgentBeans(
						agentBeanTools.copyEntityListToBeanList(agentList));
			}

			beanList.add(bean);
		}

		return beanList;
	}

}
