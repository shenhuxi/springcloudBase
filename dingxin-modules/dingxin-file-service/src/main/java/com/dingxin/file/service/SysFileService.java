package com.dingxin.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dingxin.common.util.FileUtil;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.file.entity.SysFile;
import com.dingxin.file.repository.SysFileRepository;


/**
 * 图片管理
 * @author shixh
 *
 */
@Service@Transactional
public class SysFileService extends CommonService<SysFile, Long>{

    @Autowired
    private SysFileRepository sysFileRepository;

    @Override
    public CommonRepository<SysFile, Long> getCommonRepository() {
        return sysFileRepository;
    }

	public SysFile saveSysFile(SysUserVo sysUser, MultipartFile file, String path) {
		SysFile sysFile = new SysFile();
		sysFile.setContentType(file.getContentType());
		sysFile.setSize(file.getSize());
		sysFile.setFileName(file.getOriginalFilename());
		sysFile.setPath(path);
		sysFile.setCreateUserId(sysUser.getId());
		sysFile.setCreateUserName(sysUser.getUserName());
		sysFile.setSuffix(FileUtil.getSuffix(file));
		return sysFileRepository.save(sysFile);
	}

	public boolean deleteByPath(String path) {
		try {
			sysFileRepository.deleteByPath(path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public SysFile findByPath(String path) {
		return sysFileRepository.findByPath(path);
	}
}
