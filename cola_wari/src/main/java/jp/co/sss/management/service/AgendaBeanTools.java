package jp.co.sss.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.AgendaBean;
import jp.co.sss.management.entity.Agenda;

@Service
public class AgendaBeanTools {

	public AgendaBean copyEntityToBean(Agenda entity) {
		AgendaBean bean = new AgendaBean();

		BeanUtils.copyProperties(entity, bean);

		return bean;
	}

    public List<AgendaBean> copyEntityListToBeanList(List<Agenda> entityList) {
        List<AgendaBean> beanList = new ArrayList<AgendaBean>();

		for (Agenda entity : entityList) {
			AgendaBean bean = new AgendaBean();
			BeanUtils.copyProperties(entity, bean);

			beanList.add(bean);
		}

		return beanList;
    }
}
