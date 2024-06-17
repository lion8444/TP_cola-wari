package jp.co.sss.management.service;

import java.util.List;

public interface BeanTools {
    public Object copyFormToBean(Object form);

    public Object copyFormToEntity(Object form);

    public Object copyEntityToBean(Object entity);

    public Object copyEntityToForm(Object entity);

    public List<Object> copyFormListToEntityList(List<Object> formList);

    public List<Object> copyEntityListToBeanList(List<Object> entityList);
}
