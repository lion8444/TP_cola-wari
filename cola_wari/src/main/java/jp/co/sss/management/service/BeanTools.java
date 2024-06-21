package jp.co.sss.management.service;

import org.springframework.stereotype.Service;


@Service
public class BeanTools {
	/**
	 * CompanyFormクラスの各フィールドの値をCompanyエンティティにコピー
	 *
	 * @param form コピー元のオブジェクト
	 * @return コピー先のオブジェクト
	 */
	
	/**
	public Company copyCompanyFormToEntity(CompanyForm formC, AgentForm formA) {
		ComCagetory category = new ComCagetory();
		Company entityC = new Company();
		Agent entityA = new Agent();
		
		BeanUtils.copyProperties(formC, entityC);
		BeanUtils.copyProperties(formA, entityA);
		
		if (formC.getComId() != null) {
			entityC.setComId(formC.getComId());
		}
		
		if(formA.getAgentId() != null) {
			entityA.setAgentId(formA.getAgentId());
		}
		category.setCateId(formC.getCateId());
		entityC.setComCagetory(category);
		
		return entityC;
	}*/
	
	/**
	 * CompanyFormクラスの各フィールドの値をCompanyエンティティにコピー
	 *
	 * @param form コピー元のオブジェクト
	 * @return コピー先のエンティティ
	 */
	/**
    public CompanyBean copyCompanyFormToEntity(CompanyForm form) {
    	CompanyBean bean = new CompanyBean();
    	
    	BeanUtils.copyProperties(form, bean);
    	
    	bean.setComId(form.getComId());
    	
    	return bean;
    }*/

}
