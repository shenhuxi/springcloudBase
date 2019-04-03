package com.dingxin.system.repository.dictionary;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysDictGroup;
import com.dingxin.system.vo.SysDictGroupItemVO;


/**  
* @ClassName: SysDictGroupRepository  
* @Description:
* @author luozb  
* @date 2018年6月14日 上午11:01:48  
*    
*/
@Repository
public interface SysDictGroupRepository extends CommonRepository<SysDictGroup,Long> {

	SysDictGroup findByDictNameAndDeleteState(String dictName,int normal);
	
	SysDictGroup findByDictCodeAndDeleteState(String dictCode,int normal);

	SysDictGroup findById(Long parentId);

	void deleteByIdIn(List<Long> longList);
	
	List<SysDictGroup> findByParentId(Long id);

	List<SysDictGroup> findByParentIdAndIdNotIn(Long parentId, Long id);	

	List<SysDictGroup> findByParentIdAndDeleteState(Long id, int normal);

	SysDictGroup findByIdAndDeleteState(Long id, int normal);
	

	List<SysDictGroup> findByParentIdAndDeleteStateAndIdNotIn(Long parentId, int normal, Long id);

	List<SysDictGroup> findByDeleteState(int normal);
	
	@Query(value = "select g.dict_code,g.dict_name,i.item_showname,i.option_value,i.sort_no as isort_no from sys_dict_group as g " + 
			"left join sys_dict_item as i on g.dict_code=i.group_code where g.id=:id and g.delete_state=:normal and i.delete_state=:normal order by isort_no",nativeQuery = true)
	List<SysDictGroupItemVO> findDictItemByIdAndDeleteState(@Param("id")Long id, @Param("normal")int normal);

}
