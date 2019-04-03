package com.dingxin.data.jpa.repository;
import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;  

/**
 * 
* Title: MyRepositoryFactory 
* Description:  自定义RepositoryFactory
* @author dicky  
* @date 2018年7月4日 下午7:58:02
 */
public class MyRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {  

	private final EntityManager entityManager;
	
    public MyRepositoryFactory(EntityManager entityManager) {  
        super(entityManager);
        this.entityManager = entityManager;
    }  
    
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    protected JpaRepository getTargetRepository(RepositoryMetadata metadata) {  
        return new MyCustomRepository<T, I>((Class<T>)metadata.getDomainType(),entityManager);  
    }  

    @Override  
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {  
        return MyCustomRepository.class;  
    }  
}  