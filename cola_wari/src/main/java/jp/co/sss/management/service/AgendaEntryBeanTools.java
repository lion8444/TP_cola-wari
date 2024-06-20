//package jp.co.sss.management.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import jp.co.sss.management.bean.AgendaEntryBean;
//import jp.co.sss.management.entity.AgendaEntry;
//import jp.co.sss.shop.bean.ItemBean;
//
//@Service
//public class AgendaEntryBeanTools {
//
//	public List<AgendaEntryBean> copyEntityListToItemBeanList(List<AgendaEntry> entityList) {
//		List<ItemBean> beanList = new ArrayList<ItemBean>();
//
//		for (Item entity : entityList) {
//			ItemBean bean = new ItemBean();
//			BeanUtils.copyProperties(entity, bean);
//
//			if (entity.getCategory() != null) {
//				bean.setCategoryName(entity.getCategory().getName());
//			}
//
//			beanList.add(bean);
//		}
//
//		return beanList;
//	}
//	
//}
