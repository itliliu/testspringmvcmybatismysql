package com.we.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.Client;
import com.we.bean.UserClientMap;
import com.we.service.IClientService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "client")
public class ClientController {
	private static Logger logger = Logger.getLogger(ClientController.class);
	private String clientID = "clientID";
	private String clientName = "clientName";
	private String clientParentID = "clientParentID";
	private String clientStatus = "clientStatus";
	private String clientType = "clientType";

	@Resource
	private IClientService clientService;

	/**
	 * Get all parent clients by userid.
	 * 
	 * @param userid
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/parent.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto selectParentByUid(int userid, HttpServletRequest request, HttpServletResponse reponse) {
		try {
			List<Map<String, Object>> listMap = clientService.selectParentByUid(userid);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, listMap);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "Get Parent Error", null);
		}
	}

	/**
	 * Get child clients by userid and parentid.
	 * 
	 * @param userid
	 * @param parentid
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/child.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto selectChild(int userid, int parentid, HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Map<String, Object> map = clientService.selectChildByUidAndParentId(userid, parentid);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, map);
		} catch (Exception e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "Get Child Error", null);
		}
	}

	/**
	 * Set user's default client
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/setdefault.json", method = RequestMethod.PUT)
	@ResponseBody
	public ResultDto setDefault(HttpServletRequest request, HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			UserClientMap userClientMap = mapper.readValue(message, UserClientMap.class);
			clientService.setDefault(userClientMap);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (SQLException | IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "Get setDefault Error", null);
		}

	}

	/**
	 * Get all client.
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/getall.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getAll(HttpServletRequest request, HttpServletResponse reponse) {
		try {
			List<Client> list = clientService.getAll();
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, list);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "Get getall Error", null);
		}

	}

	/**
	 * Get client by userid
	 * 
	 * @param userid
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/getclientbyuserid.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getClientByUserid(Integer userid, HttpServletRequest request, HttpServletResponse reponse) {
		try {
			List<Client> list = clientService.getClientByUserid(userid);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, list);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "Get getclientbyuserid Error", null);
		}

	}

	/**
	 * Add new client.
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto add(HttpServletRequest request, HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Client client = mapper.readValue(message, Client.class);
			if (clientService.selectByClientName(client.getClientname()) != null) {
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.NACK, "Already have a same client name", null);
			}
			int returnCode = clientService.add(client);
			if (returnCode == -1) {
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.NACK, "Already have a same client name", client.getClientid());
			} else if (returnCode == 0) {
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.NACK, "Add client failed", returnCode);
			} else {
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, returnCode);
			}

		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit parameter error ", null);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit sql error ", null);
		}
	}

	/**
	 * update a client.
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/deactivate.json", method = RequestMethod.PUT)
	@ResponseBody
	public ResultDto deleteById(HttpServletRequest request, HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Client client = mapper.readValue(message, Client.class);
			Map<String, Object> clientMap = new HashMap<String, Object>();
			clientMap.put(clientID, client.getClientid());
			clientMap.put(clientName, client.getClientname());
			clientMap.put(clientParentID, client.getParentid());
			clientMap.put(clientStatus, client.getStatus());
			clientMap.put(clientType, "deactivate");

			int returnCode = clientService.editClient(clientMap);
			if (returnCode == Constant.SERVER_ERROR) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.EXECUTE_SQL_ERROR, null);
			} else if (returnCode == Constant.TEMPLATE_IS_USED) {
				return new ResultDto(Constant.NACK, Constant.DELETE_CLIENT_FAILED, null);
			}
			
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "delete parameter error ", null);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "delete sql error", null);
		}
	}

	/**
	 * Edit a client.
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/edit.json", method = RequestMethod.PUT)
	@ResponseBody
	public ResultDto edit(HttpServletRequest request, HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Client client = mapper.readValue(message, Client.class);
			Map<String, Object> clientMap = new HashMap<String, Object>();
			clientMap.put(clientID, client.getClientid());
			clientMap.put(clientName, client.getClientname());
			clientMap.put(clientParentID, client.getParentid());
			clientMap.put(clientStatus, client.getStatus());
			clientMap.put(clientType, "edit");

			int returnCode = clientService.editClient(clientMap);
			if (returnCode == Constant.SERVER_ERROR) {
				return new ResultDto(Constant.NACK, Constant.EXECUTE_SQL_ERROR, null);
			} else if (returnCode == Constant.TEMPLATE_IS_USED) {
				return new ResultDto(Constant.NACK, Constant.DELETE_CLIENT_FAILED, null);
			}
			
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit parameter error ", null);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit sql error ", null);
		}
	}

	@RequestMapping(value = "/getclienttree.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getClientTree(HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Map<String, Object> map = clientService.getTree();
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, map);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "Get getclienttree Error", null);
		}

	}
}
