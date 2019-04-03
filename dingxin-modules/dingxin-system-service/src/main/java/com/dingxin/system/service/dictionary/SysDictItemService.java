package com.dingxin.system.service.dictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.BeanUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysDictGroup;
import com.dingxin.system.entity.SysDictItem;
import com.dingxin.system.repository.dictionary.SysDictGroupRepository;
import com.dingxin.system.repository.dictionary.SysDictItemRepository;

/**
 * @ClassName: SysDictItemService
 * @Description: 数据字典类型业务逻辑处理
 * @author luozb
 * @date 2018年6月14日 上午9:41:40
 * 
 */
@Service
@Transactional(rollbackFor = RestException.class)
public class SysDictItemService extends CommonService<SysDictItem, Long> {

	@Resource
	public RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private SysDictItemRepository sysDictItemRepository;

	@Autowired
	private SysDictGroupRepository sysDictGroupRepository;

	@Override
	public CommonRepository<SysDictItem, Long> getCommonRepository() {
		return sysDictItemRepository;
	}

	/**
	 * 新增,更新
	 * @param sysUser
	 * @param sysDictItem
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws RestException
	 */
	public ResultObject saveSysDictItem(SysUserVo sysUser, SysDictItem sysDictItem)
			throws IllegalArgumentException, IllegalAccessException, RestException {
		SysDictGroup sysDictGroup = sysDictGroupRepository.findByIdAndDeleteState(sysDictItem.getGroupId(),
				CommonConstant.NORMAL);
		if (sysDictGroup == null) {
			return ResultObject.fail("字典类型不存在！");
		}
		SysDictItem sysDictItemDB = sysDictItemRepository
				.findByItemShownameAndDeleteState(sysDictItem.getItemShowname(), CommonConstant.NORMAL);
		SysDictItem sysDictItemDB1 = sysDictItemRepository.findByOptionValueAndGroupCodeAndDeleteState(
				sysDictItem.getOptionValue(), sysDictItem.getGroupCode(), CommonConstant.NORMAL);
		SysDictItem sdg = null;
		if (sysDictItem.getId() == null) { // add
			if (sysDictItemDB != null) {
				return ResultObject.fail("请不要添加重复数据字典内容！");
			}
			if (sysDictItemDB1 != null) {
				return ResultObject.fail("同一字典类型的选项值不能重复！");
			}
			sysDictItem.setGroupCode(sysDictGroup.getDictCode());
			sdg = this.save(sysDictItem);
		} else { // update
			SysDictItem one = sysDictItemRepository.findById(sysDictItem.getId());
			if (one == null) {
				throw new RestException("更新操作id不存在!");
			}
			BeanUtil.copyPropertiesIgnoreNull(sysDictItem, one);
			one.setGroupCode(sysDictGroup.getDictCode());
			sdg = this.save(one);
		}

		// 刷新redis的值
		List<String> keys = new ArrayList<String>();
		keys.add(CommonConstant.DICTITEM + sysDictGroup.getDictCode());
		keys.add(CommonConstant.DICTITEM + sysDictGroup.getId());
		redisTemplate.delete(keys);

		return ResultObject.ok("操作成功！", sdg);
	}

	/**
	 * 删除字典明细
	 * 
	 * @param ids
	 * @return ResultObject
	 */
	public ResultObject deleteByIds(SysUserVo sysUser, String ids) {
		String[] strArr = ids.split(",");
		List<String> list = Arrays.asList(strArr);
		List<Long> longList = new ArrayList<>();
		for (String str : list) {
			long i = Long.parseLong(str);
			longList.add(i);
		}
		List<SysDictItem> itemList = new ArrayList<>();
		Set<String> keys = new HashSet<String>();
		for (long id : longList) {
			SysDictItem sdg = sysDictItemRepository.findByIdAndDeleteState(id, CommonConstant.NORMAL);
			sdg.setModifyUserId(sysUser.getId());
			sdg.setModifyUserName(sysUser.getUserName());
			sdg.setDeleteState(CommonConstant.DELETE);
			itemList.add(sdg);
			
			String dictCode = sdg.getGroupCode();
			SysDictGroup psdg = sysDictGroupRepository.findByDictCodeAndDeleteState(dictCode, CommonConstant.NORMAL);
			keys.add(CommonConstant.DICTITEM + psdg.getDictCode());
			keys.add(CommonConstant.DICTITEM + sdg.getId());
		}
		sysDictItemRepository.save(itemList);

		// 刷新redis的值
		redisTemplate.delete(keys);
		
		return ResultObject.ok("删除成功!");
	}

	/**
	 * 通过groupId查询明细
	 * @param groupId
	 * @param normal
	 * @return
	 */
	public List<SysDictItem> findByGroupIdAndDeleteState(long groupId, int normal) {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		if (!redisTemplate.hasKey(CommonConstant.DICTITEM + groupId)) {
			List<SysDictItem> list = sysDictItemRepository.findByGroupIdAndDeleteState(groupId, normal);
			operations.set(CommonConstant.DICTITEM + groupId, list, CommonConstant.WEEK, TimeUnit.SECONDS);
		}
		return (List<SysDictItem>) operations.get(CommonConstant.DICTITEM + groupId);
	}

	/**
	 * 通过groupCode查询明细
	 * @param groupId
	 * @param normal
	 * @return
	 */
	public List<SysDictItem> findByGroupCodeAndDeleteState(String groupCode, int normal) {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		// 先查看 缓存内是否有数据
		if (!redisTemplate.hasKey(CommonConstant.DICTITEM + groupCode)) {
			List<SysDictItem> list = sysDictItemRepository.findByGroupCodeAndDeleteState(groupCode, normal);
			operations.set(CommonConstant.DICTITEM + groupCode, list, CommonConstant.WEEK, TimeUnit.SECONDS);
		}
		return (List<SysDictItem>) operations.get(CommonConstant.DICTITEM + groupCode);
	}

	public List<SysDictItem> findByGroupId(long groupId) {
		return sysDictItemRepository.findByGroupId(groupId);
	}
	
	public SysDictItem findByIdAndDeleteState(Long id, int normal) {
		return sysDictItemRepository.findByIdAndDeleteState(id, normal);
	}

}
