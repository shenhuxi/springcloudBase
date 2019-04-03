package com.dingxin.system.repository.dictionary;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysDictItem;

/**
 * @ClassName: SysDictItemRepository
 * @Description: 字典明细表Dao
 * @author luozb
 * @date 2018年6月14日 上午11:31:48
 * 
 */
@Repository
public interface SysDictItemRepository extends CommonRepository<SysDictItem, Long> {

	SysDictItem findByItemShownameAndDeleteState(String itemShowName, int normal);

	SysDictItem findByIdAndDeleteState(long id, int normal);
	
	SysDictItem findById(long id);

	List<SysDictItem> findByGroupCodeAndDeleteState(String groupCode, int normal);
	
	List<SysDictItem> findByGroupIdAndDeleteState(long groupId, int normal);
	
	List<SysDictItem> findByGroupId(long groupId);

	SysDictItem findByOptionValueAndGroupCodeAndDeleteState(String optionValue, String groupCode, int normal);

}
