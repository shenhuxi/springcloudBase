package com.dingxin.file.repository;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.file.entity.SysFile;


/**
 * 图片资源
 * @author shixh
 */
@Repository
public interface SysFileRepository extends CommonRepository<SysFile, Long> {
	
	@Modifying@Transactional
	@Query("delete from SysFile where path =:path")
	void deleteByPath(@Param("path") String path);

	SysFile findByPath(String path);
	

}
