package com.we.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "role")
public class RoleController {
	
}
