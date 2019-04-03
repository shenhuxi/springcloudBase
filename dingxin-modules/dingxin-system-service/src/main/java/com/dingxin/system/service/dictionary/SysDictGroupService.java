package com.dingxin.system.service.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.BaseTreeUtil;
import com.dingxin.common.util.BeanUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysDictGroup;
import com.dingxin.system.entity.SysDictItem;
import com.dingxin.system.repository.dictionary.SysDictGroupRepository;
import com.dingxin.system.repository.dictionary.SysDictItemRepository;
import com.dingxin.system.vo.SysDictGroupItemVO;

/**
 * @ClassName: SysDictGroupService
 * @Description: 数据字典类型业务逻辑处理
 * @author luozb
 * @date 2018年6月14日 上午9:41:40
 * 
 */
@Service
@Transactional(rollbackFor = RestException.class)
public class SysDictGroupService extends CommonService<SysDictGroup, Long> {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private SysDictGroupRepository sysDictGroupRepository;

	@Autowired
	private SysDictItemRepository sysDictItemRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public CommonRepository<SysDictGroup, Long> getCommonRepository() {
		return sysDictGroupRepository;
	}

	public ResultObject saveSysDictGroup(SysUserVo sysUser, SysDictGroup sysDictGroup)
			throws RestException, IllegalArgumentException, IllegalAccessException {

		SysDictGroup sysDictGroupDB = sysDictGroupRepository.findByDictNameAndDeleteState(sysDictGroup.getDictName(),
				CommonConstant.NORMAL);
		SysDictGroup sdg = null;
		if (sysDictGroup.getId() == null) { // add
			if (sysDictGroupDB != null) {
				return ResultObject.fail("请不要添加重复数据字典类型！");
			}
			// 维护字典类型表的isParent字段,如果增加了子节点，将isParent置为0
			sysDictGroup.setCreateUserId(sysUser.getId());
			sysDictGroup.setCreateUserName(sysUser.getUserName());
			Long pid = sysDictGroup.getParentId();
			if (pid != 0) {
				SysDictGroup psdg = sysDictGroupRepository.findById(sysDictGroup.getParentId());
				if (psdg != null) {
					psdg.setIsParent(CommonConstant.IS_PARENT);
					// 根据parnentId获得parentName
					sysDictGroup.setParentName(psdg.getDictName());
					sysDictGroupRepository.saveAndFlush(psdg);
				} else {
					return ResultObject.fail("请先录入父级字典类型！");
				}
			}
			sdg = this.save(sysDictGroup);
		} else { // update
			SysDictGroup one = sysDictGroupRepository.findByIdAndDeleteState(sysDictGroup.getId(),
					CommonConstant.NORMAL);
			if (one == null) {
				throw new RestException("更新操作id不存在!");
			}
			sysDictGroup.setModifyUserId(sysUser.getId());
			sysDictGroup.setModifyUserName(sysUser.getUserName());
			BeanUtil.copyPropertiesIgnoreNull(sysDictGroup, one);
			sdg = this.save(one);
		}
		//一旦修改 删除redis缓存的静态树
		redisTemplate.delete(CommonConstant.DICTSTATICTREE);
		return ResultObject.ok("操作成功！", sdg);
	}

	/**
	 * 1.判断字典类型下是否存在字典明细,须先删除明细数据才能删除！
	 * 2.通过父级编号=传入的id找到所有子级节点，如果不为空则说明节点下存在子节点，不能删除，如果为空说明该id没有子节点，可以删除
	 * 3.在可以删除的前提下，维护isParent字段：通过该节点的父id找到所有子节点且子节点不在传入id列表中的同级节点，如果没有同级节点，
	 * 说明该全部删除了，需将父级机构的isParent字段置为1
	 * 
	 * @param id
	 * @return ResultObject
	 */
	public ResultObject deleteById(SysUserVo sysUser, Long id) {
		SysDictItem di = sysDictItemRepository.findByIdAndDeleteState(id, CommonConstant.NORMAL);
		if (di != null) {
			List<SysDictItem> dItems = sysDictItemRepository.findByGroupCodeAndDeleteState(di.getGroupCode(),
					CommonConstant.NORMAL);
			if (!CollectionUtils.isEmpty(dItems))
				return ResultObject.fail("字典类型下存在明细数据,须先删除明细数据才能删除！");
		}
		List<SysDictGroup> sdgs = sysDictGroupRepository.findByParentIdAndDeleteState(id, CommonConstant.NORMAL);
		if (!CollectionUtils.isEmpty(sdgs))
			return ResultObject.fail("字典类型树下存在子类型,不能进行删除！");

		SysDictGroup sdg = sysDictGroupRepository.findByIdAndDeleteState(id, CommonConstant.NORMAL);
		List<SysDictGroup> sdgroup = sysDictGroupRepository.findByParentIdAndDeleteStateAndIdNotIn(sdg.getParentId(),
				CommonConstant.NORMAL, id);
		if (CollectionUtils.isEmpty(sdgroup)) {
			SysDictGroup psdg = sysDictGroupRepository.findByIdAndDeleteState(sdg.getParentId(), CommonConstant.NORMAL);
			psdg.setIsParent(CommonConstant.NO_PARENT);
			sysDictGroupRepository.saveAndFlush(psdg);
		}

		sdg.setModifyUserId(sysUser.getId());
		sdg.setModifyUserName(sysUser.getUserName());
		sdg.setDeleteState(CommonConstant.DELETE);
		sysDictGroupRepository.save(sdg);
		//一旦修改 删除redis缓存的静态树
		redisTemplate.delete(CommonConstant.DICTSTATICTREE);
		return ResultObject.ok("删除成功!");
	}

	public List<TreeBean> queryDictTreeByParentId(long parentId) {
		List<SysDictGroup> dictList = sysDictGroupRepository.findByParentIdAndDeleteState(parentId,
				CommonConstant.NORMAL);
		if (!CollectionUtils.isEmpty(dictList)) {
			List<TreeBean> treeList = new ArrayList<>();
			dictList.forEach(dict -> {
				treeList.add(new TreeBean(dict.getId() + "", dict.getDictName(), dict.getParentId() + "",
						dict.getIsParent() + "", dict.getParentName() + ""));
			});
			return treeList;
		}
		return Collections.emptyList();

	}

	/**
	 * 构建字典组树(redis缓存)
	 * 
	 * @return
	 */
	public List<TreeBean> searchAllDictTree() {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		if(! redisTemplate.hasKey(CommonConstant.DICTSTATICTREE)) {
			List<SysDictGroup> dictList = sysDictGroupRepository.findByDeleteState(CommonConstant.NORMAL);
			if (!CollectionUtils.isEmpty(dictList)) {
				List<TreeBean> treeList = new ArrayList<>();
				dictList.forEach(dict -> {
					treeList.add(new TreeBean(dict.getId() + "", dict.getDictName(), dict.getParentId() + "",
							dict.getIsParent() + "", dict.getParentName() + ""));
				});
				operations.set(CommonConstant.DICTSTATICTREE, BaseTreeUtil.buildListToTree(treeList), CommonConstant.WEEK, TimeUnit.SECONDS);;
			}else {
				return Collections.emptyList();
			}
		}
		return (List<TreeBean>) operations.get(CommonConstant.DICTSTATICTREE);
	}

	public List<SysDictGroupItemVO> findByIdAndDeleteState(Long id, int normal) {
		return sysDictGroupRepository.findDictItemByIdAndDeleteState(id, normal);

	}

}
