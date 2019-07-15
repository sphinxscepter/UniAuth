package com.yyt.UniAuth.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yyt.UniAuth.model.dto.ListDataDto;
import com.yyt.UniAuth.model.entity.App;
import com.yyt.UniAuth.model.entity.Tenant;
import com.yyt.UniAuth.service.TenantService;
import com.yyt.UniAuth.utils.ExceptionDetail;
import com.yyt.UniAuth.utils.ReturnMessageUtils;
import com.yyt.UniAuth.utils.TextUtil;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/tenant")
public class TenantController {
	
	private static Logger logger = LoggerFactory.getLogger(TenantController.class);
	
	@Autowired
	private TenantService tenantService;
	
	@PostMapping("addTenant")
	public Map<String, Object> addTenant(@RequestBody Tenant tenant){
		try {
			String rlt = tenantService.addTenant(tenant);
			if("OK".equals(rlt)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.SUCCESS, "添加租户成功", tenant.getId());
			} else {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, rlt, null);
			}
		} catch (Exception e) {
			logger.error("添加租户异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "添加租户异常", null);
		}
	}
	
	@GetMapping("delTenant")
	public Map<String, Object> delTenant(HttpServletRequest request){
		String tenantIdStr = request.getParameter("tenantId");
		if(null == tenantIdStr || "".equals(tenantIdStr)) {
			logger.warn("要删除的租户ID为空");
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "要删除的租户ID为空", null);
		} else {
			if(!TextUtil.isNumber(tenantIdStr)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, "要删除的租户ID只能是数字", null);
			}
		}
		try {
			Integer tenantId = Integer.valueOf(tenantIdStr); 
			String rlt = tenantService.delTenant(tenantId);
			if("OK".equals(rlt)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.SUCCESS, "删除租户成功", null);
			} else {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, rlt, null);
			}
		} catch (Exception e) {
			logger.error("添加租户异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "添加租户异常", null);
		}
	}
	
	@PostMapping("addApp")
	public Map<String, Object> addApp(@RequestBody App app){
		if(null == app.getTenantId() || 0 == app.getTenantId()) {
			logger.warn("要添加应用的租户ID为空");
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "要添加应用的租户ID为空", null);
		}
		if(null == app.getAppName() || "".equals(app.getAppName())) {
			logger.warn("要添加应用的名称为空");
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "要添加应用的名称为空", null);
		}
		try {
			String rlt = tenantService.addApp(app);
			if("OK".equals(rlt)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.SUCCESS, "添加应用成功", null);
			} else {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, rlt, null);
			}
		} catch (Exception e) {
			logger.error("添加应用异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "添加应用异常", null);
		}
	}
	
	@PostMapping("updateAppSecret")
	public Map<String, Object> updateAppSecret(@RequestBody App app){
		if(null == app.getId() || 0 == app.getId()) {
			logger.warn("应用ID为空");
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "应用ID为空", null);
		}
		try {
			String rlt = tenantService.updateAppSecret(app);
			if("OK".equals(rlt)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.SUCCESS, "修改应用安全码成功", null);
			} else {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, rlt, null);
			}
		} catch (Exception e) {
			logger.error("修改应用安全码异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "修改应用安全码异常", null);
		}
	}
	
	@PostMapping("delApp")
	public Map<String, Object> delApp(@RequestBody App app){
		if(null == app.getId() || 0 == app.getId()) {
			logger.warn("应用ID为空");
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "应用ID为空", null);
		}
		try {
			String rlt = tenantService.delApp(app);
			if("OK".equals(rlt)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.SUCCESS, "删除应用成功", null);
			} else {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, rlt, null);
			}
		} catch (Exception e) {
			logger.error("删除应用异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "删除应用异常", null);
		}
	}
	
	@GetMapping("getApps")
	public Map<String, Object> getApps(HttpServletRequest request){
		String tenantIdStr = request.getParameter("tenantId");
		if(null == tenantIdStr || "".equals(tenantIdStr)) {
			logger.warn("租户ID为空");
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "租户ID为空", null);
		} else {
			if(!TextUtil.isNumber(tenantIdStr)) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, "要删除的租户ID只能是数字", null);
			}
		}
		try {
			ListDataDto<App> dto = 
					tenantService.getAppsByTenantId(Integer.valueOf(tenantIdStr));
			if(dto.getTotal() == 0) {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.ERROR, "该租户下没有应用", dto);
			} else {
				return ReturnMessageUtils.genReturnMsg(
						ReturnMessageUtils.SUCCESS, null, dto);
			}
		} catch (Exception e) {
			logger.error("获取应用异常");
			logger.error(ExceptionDetail.getTrace(e));
			return ReturnMessageUtils.genReturnMsg(
					ReturnMessageUtils.ERROR, "获取应用异常", null);
		}
	}
}
