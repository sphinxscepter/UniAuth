package com.yyt.UniAuth.customoauth;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yyt.UniAuth.model.dto.ListDataDto;
import com.yyt.UniAuth.model.entity.App;
import com.yyt.UniAuth.model.entity.Su360ClientDetail;
import com.yyt.UniAuth.model.entity.User;
import com.yyt.UniAuth.service.Su360ClientDetailService;
import com.yyt.UniAuth.service.TenantService;
import com.yyt.UniAuth.service.UserService;
import com.yyt.UniAuth.utils.HttpUtils;

@Component
public class CustomOAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private Su360ClientDetailService su360ClientDetailService;
	@Autowired
	private UserService userService;
	@Autowired
	private TenantService tenantService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(!"/api/v1/auth/token".equals(request.getRequestURI()) &&
				!"password".equals(request.getParameter("grant_type"))) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String[] clientDetails = this.getClientDetails(request);
		if(null == clientDetails) {
			BaseResponse br = HttpResponse.baseResponse(
					HttpStatus.UNAUTHORIZED.value(),
					"请求中未包含客户端信息");
			HttpUtils.writerError(br, response);
		}
		User user = userService.getUserInfoByMobile(clientDetails[2]);
		logger.info("user mobile = " + user.getUsername());
		ListDataDto<App> dto = tenantService.getAppsByTenantId(user.getTenantId());
		if(dto.getTotal() == 0) {
			logger.info("user apps = " + dto.getTotal());
			BaseResponse br = HttpResponse.baseResponse(
					HttpStatus.UNAUTHORIZED.value(),
					"权限不足");
			logger.info("user权限不足");
			HttpUtils.writerError(br, response);
		} else {
			List<App> apps = dto.getDataList();
			boolean matches = false;
			for(App app : apps) {
				logger.info("user appId = " + app.getAppId());
				if(clientDetails[0].equals(app.getAppId())) {
					matches = true;
					logger.info("user app matched");
					break;
				}
			}
			if(!matches) {
				logger.info("user权限不足");
				BaseResponse br = HttpResponse.baseResponse(
						HttpStatus.UNAUTHORIZED.value(),
						"权限不足");
				HttpUtils.writerError(br, response);
			}
		}
		
		this.handle(request, response, clientDetails, filterChain);
	}
	
	private void handle(HttpServletRequest request,
			HttpServletResponse response,
			String[] clientDetails,
			FilterChain filterChain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null != authentication && authentication.isAuthenticated()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Su360ClientDetail clientDetail = 
				(Su360ClientDetail) su360ClientDetailService.loadClientByClientId(clientDetails[0]);
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(
						clientDetail.getClientId(), clientDetail.getClientSecret());
		SecurityContextHolder.getContext().setAuthentication(token);
		filterChain.doFilter(request, response);
	}
	
	
	
	// 判断请求头中是否包含client信息，不包含返回false
	private String[] getClientDetails(HttpServletRequest request) {
		String[] params = null;
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(null == authHeader) {
			String id = request.getParameter("client_id");
			String secret = request.getParameter("client_secret");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(id != null) {
				params = new String[] {id, secret, username, password};
			}
		} else {
			String basic = authHeader.substring(0, 5);
			if(basic.toLowerCase().contains("basic")) {
				String tmp = authHeader.substring(6);
				String defaultClientDetail = new String(Base64.getDecoder().decode(tmp));
				String[] clientDetails = defaultClientDetail.split(":");
				if(clientDetails.length != 2) {
					return params;
				} else {
					params = clientDetails;
				}
			}
		}
		return params;
	}

}
